package com.arms.jiracloud.controller;


import com.arms.jiracloud.testjira;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j
@Controller
@RequestMapping(value = {"/arms/jiraCloud"})
public class JiraCloudController {

    @Autowired
    private final testjira jiraClient;

    @Autowired
    public JiraCloudController(testjira jiraClient) {
        this.jiraClient = jiraClient;
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
}
