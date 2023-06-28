package com.wxw.springbootdemo.controller;

import com.wxw.springbootdemo.pubg.entry.ActionInfo;
import com.wxw.springbootdemo.pubg.entry.ActualTimeTeamInfo;
import com.wxw.springbootdemo.pubg.service.IntegralService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pubg")
public class PUBGController {

    @Resource
    private IntegralService integralService;

    @GetMapping("/test")
    public void test() {
        List<ActualTimeTeamInfo> teams = createTeams();
        integralService.initTeamInfo(null, teams);
        gameStart("1", "2");
        gameStart("1", "4");
        gameStart("1", "11");
        gameStart("1", "12");
        gameStart("6", "10");
        gameStart("5", "6");
        gameStart("4", "5");
        gameStart("7", "8");
        gameStart("7", "9");
        gameStart("3", "1");
        gameStart("3", "7");
        gameStart("13", "3");
        gameStart("13", "14");
        gameStart("13", "15");
        gameStart("16", "13");
    }

    private List<ActualTimeTeamInfo> createTeams() {
        List<ActualTimeTeamInfo> list = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            ActualTimeTeamInfo teamInfo = new ActualTimeTeamInfo();
            teamInfo.setId(""+i);
            teamInfo.setTeamName("team-"+i);
            teamInfo.setSurvivalCount(4);
            list.add(teamInfo);
        }
        return list;
    }

    private void gameStart(String leftTeam, String rightTeam) {
        sendAction(leftTeam + "0001", leftTeam, rightTeam+"0001", rightTeam, "击倒", null);
        sendAction(leftTeam + "0001", leftTeam, rightTeam+"0002", rightTeam, "击倒", null);
        sendAction(leftTeam + "0001", leftTeam, rightTeam+"0003", rightTeam, "击倒", null);
        sendAction(leftTeam + "0001", leftTeam, rightTeam+"0004", rightTeam, "击杀", null);
    }

    private void sendAction(String attackedPlayerId, String attackedPlayerTeamId, String beAttackedPlayerId, String beAttackedPlayerTeamId, String attackResult, String attackMode) {
        ActionInfo actionInfo_1 = new ActionInfo(attackedPlayerId, attackedPlayerTeamId, beAttackedPlayerId, beAttackedPlayerTeamId, attackResult, attackMode);
        System.out.println(attackedPlayerId + " " + attackResult + " " + beAttackedPlayerId);
        integralService.countScore(null, actionInfo_1);
        List<ActualTimeTeamInfo> allTeamRank = integralService.getAllTeamRank(null);
        System.out.println("----------------------------");
        System.out.println("--队名-------存活数--------积分--");
        allTeamRank.forEach(teamInfo -> System.out.println(
                        "--"+teamInfo.getTeamName()+"-------"+teamInfo.getSurvivalCount()+"--------"+teamInfo.getTotalScore()+"--"));
        System.out.println("----------------------------");
    }

}
