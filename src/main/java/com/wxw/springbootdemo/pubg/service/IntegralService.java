package com.wxw.springbootdemo.pubg.service;

import com.wxw.springbootdemo.pubg.entry.ActionInfo;
import com.wxw.springbootdemo.pubg.entry.ActualTimeTeamInfo;

import java.util.List;

/**
 * 积分服务
 */
public interface IntegralService {

    /**
     * 初始化积分统计集合
     */
    void initTeamInfo(List<ActualTimeTeamInfo> list);

    /**
     * 获取当前所有队伍的排名
     */
    List<ActualTimeTeamInfo> getAllTeamRank();

    /**
     * 积分计算
     * @param actionInfo
     */
    void countScore(ActionInfo actionInfo);

}
