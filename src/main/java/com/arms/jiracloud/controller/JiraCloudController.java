package com.arms.jiracloud.controller;


import com.arms.jiracloud.OAuth2jira;
import com.arms.jiracloud.testjira;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping(value = {"/arms/jiraCloud"})
public class JiraCloudController {
    private String accessToken;
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
    public ResponseEntity getIssue(String accessToken, HttpServletRequest request) {
        String issueId = "TES-3";
        HttpSession session = request.getSession();
        String sessionToken = (String) session.getAttribute("accessToken");
        System.out.println("sessionToken 조회 @@@@@@@@@@@@@@@@@" +sessionToken);
        ResponseEntity response = jiraClient.getIssue(issueId,sessionToken);

        System.out.println("이슈 조회 @@@@@@@@@@@@@@@@@" +response);

        return response;
    }

    @GetMapping("CreateIssue")
    public ResponseEntity createIssue() {
        String key = "YOUR_PROJECT_KEY";
        String summary = "Issue Summary";
        String description = "Issue Description";
        String issueType = "Bug";
        return jiraClient.createIssue(key, summary, description, issueType);
    }


//    @GetMapping("Autorize")
//    public ResponseEntity requestautorizationUrl( )  {
//        ResponseEntity response =oauthClient.requestautorizationUrl();
//        System.out.println("response 응답데이터"+response);
//        return response;
//    }

//    @GetMapping("Callback")
//    public void getAccessToken(@RequestParam String state, @RequestParam String code,HttpServletRequest request) {
//        System.out.println("코드 받아오기 :"+code);
//        accessToken = oauthClient.GetToken(code);
//
//        HttpSession session = request.getSession();
//        session.setAttribute("accessToken", accessToken);
//
//    }

    @GetMapping("callback")
    public ResponseEntity getAccessToken(@RequestParam String state, @RequestParam String code,HttpServletRequest request) {
        System.out.println("코드 받아오기 :"+code);
        accessToken = oauthClient.GetToken(code);

        HttpSession session = request.getSession();
        session.setAttribute("accessToken", accessToken);



        System.out.println("accessToken 조회 @@@@@@@@@@@@@@@@@" +accessToken);
//        ResponseEntity response = jiraClient.getIssue(issueId,accessToken);
//
//        System.out.println("이슈 조회 @@@@@@@@@@@@@@@@@" +response);

       return null;

    }


}
