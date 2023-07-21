package com.arms.jiracloud.controller;


import com.arms.jiracloud.OAuth2jira;
import com.arms.jiracloud.testjira;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Controller
@RequestMapping(value = {"/arms/jiraCloud"})
public class JiraCloudController {

    @Autowired
    private final testjira jiraClient;
    @Autowired
    private final OAuth2jira oauthClient;

    @Autowired
    public JiraCloudController(testjira jiraClient,OAuth2jira  oauthClient) {
        this.jiraClient = jiraClient;
        this.oauthClient=oauthClient;
    }

    @GetMapping("GetIssue")
    public ResponseEntity getIssue() {
        String issueId = "TES-2";
        System.out.println("이슈 조회 @@@@@@@@@@@@@@@@@" +jiraClient.getIssue(issueId) );

        return jiraClient.getIssue(issueId);
    }

    @GetMapping("CreateIssue")
    public ResponseEntity createIssue() {
        String key = "YOUR_PROJECT_KEY";
        String summary = "Issue Summary";
        String description = "Issue Description";
        String issueType = "Bug";
        return jiraClient.createIssue(key, summary, description, issueType);
    }


    @GetMapping("Autorize")
    public ResponseEntity requestautorizationUrl( )  {
        ResponseEntity response =oauthClient.requestautorizationUrl();
        System.out.println("response 응답데이터"+response);
        return response;
    }

    @GetMapping("Callback")
    public void  CallBackFunc(@RequestParam String state, @RequestParam String code) {
        System.out.println("코드 받아오기 :"+code);
        oauthClient.GetToken(code);
    }


}
