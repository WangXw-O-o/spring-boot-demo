package com.wxw.springbootdemo.pubg.service.impl;

import com.wxw.springbootdemo.pubg.entry.ActionInfo;
import com.wxw.springbootdemo.pubg.entry.ActualTimeTeamInfo;
import com.wxw.springbootdemo.pubg.service.IntegralService;
import com.wxw.springbootdemo.service.RedisHashClient;
import com.wxw.springbootdemo.service.RedisZSetClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class IntegralServiceImpl implements IntegralService {

    @Resource
    private RedisTemplate<String, ActualTimeTeamInfo> redisHashTemplate;
    @Resource
    private RedisZSetClient redisZSetClient;

    @Override
    public void initIntegral(String key, Set<ActualTimeTeamInfo> list) {

    }

    @Override
    public void updateTeamIntegral(String key, ActualTimeTeamInfo teamInfo) {
        //更新缓存中的队伍信息
        redisHashTemplate.opsForHash().put(key, teamInfo.getId(), teamInfo);
        //更新积分
        redisZSetClient.incrZSetMemberScore(key, teamInfo.getId(), Double.parseDouble(Integer.toString(teamInfo.getTotalScore())));
        //TODO 更新数据库数据
    }

    @Override
    public ActualTimeTeamInfo getTeamInfo(String key, String teamId) {
        //获取当前队伍的信息
        ActualTimeTeamInfo info = (ActualTimeTeamInfo) redisHashTemplate.opsForHash().get(key, teamId);
        if (info == null) {
            log.error("获取队伍[{}]信息失败！key={}", teamId, key);
            throw new RuntimeException("获取队伍信息失败！");
        }
        //获取排名信息
        Double score = redisZSetClient.getZSetMemberScore(key, teamId);
        if (score != null) {
            info.setRank(score.intValue());
        }
        return info;
    }

    @Override
    public List<ActualTimeTeamInfo> countScore(String key, ActionInfo actionInfo) {
        log.info("积分计算开始: actionInfo={}", actionInfo.toString());
        if ("击倒".equals(actionInfo.getAttackMode())) {
            //击倒行为：将数据记录到击倒列表中
            saveFallDownMessage(actionInfo);
        } else {
            //击杀行为
            countTeamScore(key, actionInfo);
            countPlayerScore(actionInfo);
        }
        //返回一个排名产生变化的队伍列表
        return null;
    }

    private void saveFallDownMessage(ActionInfo actionInfo) {
        //击倒行为：将数据记录到击倒列表中

    }

    private void countTeamScore(String key, ActionInfo actionInfo) {
        //从击倒列表中获取信息（击倒者，击杀者）。然后做积分结算。
        //获取击杀者所属队伍当前积分信息
        ActualTimeTeamInfo teamInfo = (ActualTimeTeamInfo) redisHashTemplate.opsForHash().get(key, actionInfo.getAttackedPlayerTeamId());

    }

    private void countPlayerScore(ActionInfo actionInfo) {
        //获取（被）击杀者选手信息，给（被）击杀者结算数据

    }
}
