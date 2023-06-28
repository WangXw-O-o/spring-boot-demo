package com.wxw.springbootdemo.pubg.entry;

import lombok.Data;

import java.io.Serializable;

/**
 * 行为信息
 */
@Data
public class ActionInfo implements Serializable {

    private static final long serialVersionUID = -7917111933579477744L;
    //攻击者
    private String attackedPlayerId;
    //攻击者队伍
    private String attackedPlayerTeamId;
    //被攻击者
    private String beAttackedPlayerId;
    //被攻击者队伍
    private String beAttackedPlayerTeamId;
    //攻击结果
    private String attackResult;
    //攻击方式
    private String attackMode;

    public ActionInfo() {
    }

    public ActionInfo(String attackedPlayerId, String attackedPlayerTeamId, String beAttackedPlayerId, String beAttackedPlayerTeamId, String attackResult, String attackMode) {
        this.attackedPlayerId = attackedPlayerId;
        this.attackedPlayerTeamId = attackedPlayerTeamId;
        this.beAttackedPlayerId = beAttackedPlayerId;
        this.beAttackedPlayerTeamId = beAttackedPlayerTeamId;
        this.attackResult = attackResult;
        this.attackMode = attackMode;
    }
}
