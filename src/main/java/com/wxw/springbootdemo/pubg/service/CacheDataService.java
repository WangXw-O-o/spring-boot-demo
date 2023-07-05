package com.wxw.springbootdemo.pubg.service;

import com.wxw.springbootdemo.pubg.entry.ActionInfo;
import com.wxw.springbootdemo.pubg.entry.ActualTimeTeamInfo;

import java.util.List;

/**
 * 缓存数据服务
 */
public interface CacheDataService {

    /**
     * 初始化队伍积分缓存
     * @param teamInfoList
     */
    void initTeamCache(List<ActualTimeTeamInfo> teamInfoList);

    /**
     * 获取队伍缓存信息
     * @param teamId
     * @return
     */
    ActualTimeTeamInfo getTeamInfo(String teamId);

    /**
     * 获取所有队伍信息
     * @return
     */
    List<ActualTimeTeamInfo> getAllTeamInfo();

    /**
     * 更新队伍缓存信息
     * @param teamInfo
     */
    void updateTeamInfo(ActualTimeTeamInfo teamInfo);

    /**
     * 更新被击倒列表
     * @param actionInfo
     */
    void updateTeamFallDownList(ActionInfo actionInfo);

    /**
     * 从被击倒列表中移除
     * @param teamId
     * @param playId
     */
    void remove2TeamFallDownList(String teamId, String playId);

    /**
     * 获取队伍所有被击倒信息
     * @param teamId
     * @return
     */
    List<ActionInfo> getTeamFallDownList(String teamId);

    /**
     * 获取当前存活队伍的数量
     * @return
     */
    int getAliveTeamCount();

    /**
     * 存活队伍数量减一
     */
    void reduceAliveTeamCount();
}
