package com.wxw.springbootdemo.pubg.service;

import com.wxw.springbootdemo.pubg.entry.ActionInfo;
import com.wxw.springbootdemo.pubg.entry.ActualTimeTeamInfo;

import java.util.List;

/**
 * 积分服务
 */
public interface IntegralService {

    /**
     * 初始化积分(构建一个当前的积分统计集合)
     */
    void initTeamInfo(String key, List<ActualTimeTeamInfo> list);

    /**
     * 获取当前积分排名
     * @param key
     */
    List<ActualTimeTeamInfo> getAllTeamRank(String key);

    /**
     * 获取单个队伍信息
     * @param key
     * @return
     */
    ActualTimeTeamInfo getTeamInfo(String key, String teamId);

    /**
     * 积分计算
     * @param actionInfo
     * @return 排名产生变化的队伍列表
     */
    void countScore(String key, ActionInfo actionInfo);

}
