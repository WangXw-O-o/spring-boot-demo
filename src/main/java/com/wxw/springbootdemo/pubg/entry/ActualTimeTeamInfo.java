package com.wxw.springbootdemo.pubg.entry;

import lombok.Data;

import java.util.List;

/**
 * 队伍信息（实时积分）
 */
@Data
public class ActualTimeTeamInfo {
    //队伍唯一标识
    private String id;
    //队伍名称
    private String teamName;
    //当前存活数
    private int survivalCount;
    //倒地数
    private int injuredCount;
    //死亡数
    private int deathCount;
    //队伍总击杀数
    private int teamKillCount;
    //队伍总排名分
    private int rankingScore;
    //总积分
    private int totalScore;
    //当前排名
    private int rank;

    public void reduceSurvivalCount() {
        this.survivalCount--;
    }

    public void addTotalScore(int score) {
        this.totalScore += score;
    }

    public boolean isOut() {
        return this.survivalCount == 0;
    }

}
