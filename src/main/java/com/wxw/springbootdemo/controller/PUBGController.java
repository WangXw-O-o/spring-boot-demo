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
        integralService.initIntegral(null, teams);
        gameStart();
    }

    private List<ActualTimeTeamInfo> createTeams() {
        List<ActualTimeTeamInfo> list = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            ActualTimeTeamInfo teamInfo = new ActualTimeTeamInfo();
            teamInfo.setId(""+i);
            teamInfo.setTeamName("team-"+i);
            teamInfo.setSurvivalCount(4);
            list.add(teamInfo);
        }
        return list;
    }

    private void gameStart() {
        ActionInfo actionInfo_1 = new ActionInfo("10001", "1", "20001", "2", "击倒", null);
        integralService.countScore(null, actionInfo_1);
        ActionInfo actionInfo_2 = new ActionInfo("10001", "1", "20002", "2", "击倒", null);
        integralService.countScore(null, actionInfo_2);
        ActionInfo actionInfo_3 = new ActionInfo("10001", "1", "20003", "2", "击倒", null);
        integralService.countScore(null, actionInfo_3);
        ActionInfo actionInfo_4 = new ActionInfo("10001", "1", "20004", "2", "击杀", null);
        integralService.countScore(null, actionInfo_4);
    }


}
