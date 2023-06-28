package com.wxw.springbootdemo.pubg.service;

import com.wxw.springbootdemo.pubg.entry.ActionInfo;
import com.wxw.springbootdemo.pubg.entry.ActualTimeTeamInfo;

import java.util.List;
import java.util.Set;

/**
 * 积分服务
 */
public interface IntegralService {

    /**
     * 初始化积分(构建一个当前的积分统计集合)
     */
    void initIntegral(String key, List<ActualTimeTeamInfo> list);

    /**
     * 更新队伍信息（积分）
     * @param key
     * @param teamInfo
     */
    void updateTeamIntegral(String key, ActualTimeTeamInfo teamInfo);

    /**
     * 获取队伍信息
     * @param key
     * @return
     */
    ActualTimeTeamInfo getTeamInfo(String key, String teamId);

    /**
     * 积分计算
     * @param actionInfo
     * @return 排名产生变化的队伍列表
     */
    List<ActualTimeTeamInfo> countScore(String key, ActionInfo actionInfo);

}
