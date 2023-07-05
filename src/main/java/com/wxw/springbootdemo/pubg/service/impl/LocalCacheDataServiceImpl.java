package com.wxw.springbootdemo.pubg.service.impl;

import com.wxw.springbootdemo.pubg.entry.ActionInfo;
import com.wxw.springbootdemo.pubg.entry.ActualTimeTeamInfo;
import com.wxw.springbootdemo.pubg.service.CacheDataService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 本地缓存实现
 */
@Service("localCacheDataService")
public class LocalCacheDataServiceImpl implements CacheDataService {

    //队伍信息缓存
    private final ConcurrentHashMap<String, ActualTimeTeamInfo> teamInfoHashMap = new ConcurrentHashMap<>(16);
    //被击倒列表
    private final ConcurrentHashMap<String, ArrayList<ActionInfo>> teamFallDownMap = new ConcurrentHashMap<>(16);
    //存活队伍数量
    private final AtomicInteger aliveTeam = new AtomicInteger();

    @Override
    public void initTeamCache(List<ActualTimeTeamInfo> teamInfoList) {
        teamInfoList.forEach((teamInfo) -> teamInfoHashMap.put(teamInfo.getId(), teamInfo));
        aliveTeam.set(teamInfoList.size());
    }

    @Override
    public ActualTimeTeamInfo getTeamInfo(String teamId) {
        return teamInfoHashMap.get(teamId);
    }

    @Override
    public List<ActualTimeTeamInfo> getAllTeamInfo() {
        List<ActualTimeTeamInfo> list = new ArrayList<>();
        teamInfoHashMap.forEach((s, teamInfo) -> list.add(teamInfo));
        return list;
    }

    @Override
    public void updateTeamInfo(ActualTimeTeamInfo teamInfo) {
        teamInfoHashMap.put(teamInfo.getId(), teamInfo);
    }

    @Override
    public void updateTeamFallDownList(ActionInfo actionInfo) {
        ArrayList<ActionInfo> actionInfos = teamFallDownMap.computeIfAbsent(actionInfo.getBeAttackedPlayerTeamId(), k -> new ArrayList<>());
        actionInfos.add(actionInfo);
    }

    @Override
    public void remove2TeamFallDownList(String teamId, String playId) {
        ArrayList<ActionInfo> actionInfos = teamFallDownMap.get(teamId);
        if (actionInfos == null || actionInfos.size() == 0) {
            return;
        }
        List<ActionInfo> collect = actionInfos.stream()
                .filter(actionInfo -> !playId.equals(actionInfo.getBeAttackedPlayerId()))
                .collect(Collectors.toList());
        teamFallDownMap.put(teamId, (ArrayList<ActionInfo>) collect);
    }

    @Override
    public List<ActionInfo> getTeamFallDownList(String teamId) {
        return teamFallDownMap.get(teamId);
    }

    @Override
    public int getAliveTeamCount() {
        return aliveTeam.get();
    }

    @Override
    public void reduceAliveTeamCount() {
        aliveTeam.getAndDecrement();
    }
}
