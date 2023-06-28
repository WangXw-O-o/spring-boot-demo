package com.wxw.springbootdemo.pubg.service.impl;

import com.wxw.springbootdemo.pubg.entry.ActionInfo;
import com.wxw.springbootdemo.pubg.entry.ActualTimeTeamInfo;
import com.wxw.springbootdemo.pubg.service.CacheDataService;
import com.wxw.springbootdemo.pubg.service.IntegralService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class IntegralServiceImpl implements IntegralService {

    @Resource
    @Qualifier("localCacheDataService")
    private CacheDataService cacheDataService;


    @Override
    public void initIntegral(String key, List<ActualTimeTeamInfo> list) {
        cacheDataService.initTeamCache(key, list);
    }

    @Override
    public void updateTeamIntegral(String key, ActualTimeTeamInfo teamInfo) {
        //更新缓存中的队伍信息
        cacheDataService.updateTeamInfo(key, teamInfo);
    }

    @Override
    public ActualTimeTeamInfo getTeamInfo(String key, String teamId) {
        return cacheDataService.getTeamInfo(key, teamId);
    }

    @Override
    public synchronized List<ActualTimeTeamInfo> countScore(String key, ActionInfo actionInfo) {
        log.info("积分计算开始: actionInfo={}", actionInfo.toString());
        if ("击倒".equals(actionInfo.getAttackMode())) {
            //击倒行为：将数据记录到击倒列表中
            cacheDataService.updateTeamFallDownList(key, actionInfo);
        } else {
            //击杀行为
            //击杀分计算
            countTeamScore(key, actionInfo);
            //排名分计算：给所有仍然存活的队伍加上当前的排名分
            countRankScore(key);
        }
        //返回一个排名产生变化的队伍列表
        //更新队伍信息
        return null;
    }

    private void countTeamScore(String key, ActionInfo actionInfo) {
        //计算击杀分
        countKillScore(key, actionInfo.getAttackedPlayerTeamId());
        //被击杀者若在倒地列表，则移出
        cacheDataService.remove2TeamFallDownList(key, actionInfo.getBeAttackedPlayerTeamId(), actionInfo.getBeAttackedPlayerId());
        //被淘汰队伍的所有倒地选手击杀分结算
        countFallDownScore(key, actionInfo.getBeAttackedPlayerTeamId());
    }

    //计算击杀分
    private void countKillScore(String key, String teamId) {
        //击杀者队伍信息
        ActualTimeTeamInfo attackedTeamInfo = cacheDataService.getTeamInfo(key, teamId);
        //击杀者队伍积分+1
        attackedTeamInfo.addTotalScore(1);
        //更新缓存数据
        cacheDataService.updateTeamInfo(key, attackedTeamInfo);
    }

    //被淘汰队伍的所有倒地选手击杀分结算
    private void countFallDownScore(String key, String teamId) {
        //被击杀者队伍信息
        ActualTimeTeamInfo beAttackedTeamInfo = cacheDataService.getTeamInfo(key, teamId);
        //淘汰队伍存活人数-1
        beAttackedTeamInfo.reduceSurvivalCount();
        cacheDataService.updateTeamInfo(key, beAttackedTeamInfo);

        //队伍被淘汰结算
        if (beAttackedTeamInfo.isOut()) {
            outTeamSettlement(key, teamId);
        }
    }

    //淘汰队伍结算
    private void outTeamSettlement(String key, String teamId) {
        //存活队伍数量-1
        cacheDataService.reduceAliveTeamCount();

        //历史倒地结算
        List<ActionInfo> teamFallDownList = cacheDataService.getTeamFallDownList(key, teamId);
        if (teamFallDownList != null && teamFallDownList.size() != 0) {
            //击杀分归属于攻击者队伍
            teamFallDownList.forEach((actionInfo -> countKillScore(key, actionInfo.getAttackedPlayerTeamId())));
        }
    }

    private void countRankScore(String key) {
        //排名分计算
        int rankScore = getRankScore(cacheDataService.getAliveTeamCount());
        log.info("当前排名分[{}]", rankScore);
        if (rankScore == 0) {
            return;
        }
        //获取所有的队伍信息，给每个未淘汰队伍都加上排名分
        cacheDataService.getAllTeamInfo(key).forEach(teamInfo -> {
                    if (!teamInfo.isOut()) {
                        teamInfo.addTotalScore(rankScore);
                    }
                });
    }

    private int getRankScore(int count) {
        if (count > 8) {
            return 0;
        } else if (count > 1) {
            // 第8名开始每轮每队都会加1分
            if (count == 7) {
                return 0; // 第七名不需要多加分
            }
            return 1;
        } else {
            return 3; // 最后获得第一的多加3分排名分
        }
    }
}
