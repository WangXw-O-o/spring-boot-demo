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
import java.util.stream.Collectors;

@Slf4j
@Service
public class IntegralServiceImpl implements IntegralService {

    @Resource
    @Qualifier("localCacheDataService")
    private CacheDataService cacheDataService;


    @Override
    public void initTeamInfo(String key, List<ActualTimeTeamInfo> list) {
        cacheDataService.initTeamCache(key, list);
    }

    @Override
    public List<ActualTimeTeamInfo> getAllTeamRank(String key) {
        //计算新排名
        List<ActualTimeTeamInfo> allTeamInfo = cacheDataService.getAllTeamInfo(key);

        return allTeamInfo.stream()
                .sorted((o1, o2) -> {
                    if (o1.getTotalScore() > o2.getTotalScore()) {
                        return -1;
                    } else if (o1.getTotalScore() == o2.getTotalScore()){
                        return 0;
                    }
                    return 1;
                }).collect(Collectors.toList());
    }

    @Override
    public ActualTimeTeamInfo getTeamInfo(String key, String teamId) {
        return cacheDataService.getTeamInfo(key, teamId);
    }

    @Override
    public synchronized void countScore(String key, ActionInfo actionInfo) {
        log.info("积分计算开始: actionInfo={}", actionInfo.toString());
        if ("击倒".equals(actionInfo.getAttackResult())) {
            //击倒行为：将数据记录到击倒列表中
            cacheDataService.updateTeamFallDownList(key, actionInfo);
            //被击倒者队伍数据更新
            updateFallDownInfo(key, actionInfo.getBeAttackedPlayerTeamId());
        } else {
            //击杀行为
            //击杀分计算
            countTeamScoreAfterKill(key, actionInfo);
            //排名分计算：给所有仍然存活的队伍加上当前的排名分
            countRankScore(key);
        }
    }

    private void countTeamScoreAfterKill(String key, ActionInfo actionInfo) {
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

    private void updateFallDownInfo(String key, String teamId) {
        //被击杀者队伍信息
        ActualTimeTeamInfo beAttackedTeamInfo = cacheDataService.getTeamInfo(key, teamId);
        //存活人数-1
        beAttackedTeamInfo.reduceSurvivalCount();
        cacheDataService.updateTeamInfo(key, beAttackedTeamInfo);
    }

    //被淘汰队伍的所有倒地选手击杀分结算
    private void countFallDownScore(String key, String teamId) {
        updateFallDownInfo(key, teamId);
        //队伍被淘汰结算
        if (cacheDataService.getTeamInfo(key, teamId).isOut()) {
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

    /**
     * 排名分：
     * 1： 10 分   1 0 1 1 1 1 1 4
     * 2： 6 分    1 0 1 1 1 1 1
     * 3： 5 分    1 0 1 1 1 1
     * 4： 4 分    1 0 1 1 1
     * 5： 3 分    1 0 1 1
     * 6： 2 分    1 0 1
     * 7： 1 分    1 0
     * 8： 1 分    1
     * 9 - 16： 0 分
     * 加积分规则：每次有队伍被淘汰，需要给剩余的队伍加上积分，每次都要加上最低的分
     * @param aliveTeamCount 存活队伍数量
     * @return
     */
    private int getRankScore(int aliveTeamCount) {
        if (aliveTeamCount > 8) {
            return 0;
        }
        if (aliveTeamCount == 8) {
            return 1;
        }
        if (aliveTeamCount == 7) {
            return 0;
        }
        if (aliveTeamCount != 1) {
            return 1;
        }
        return 4;
    }
}
