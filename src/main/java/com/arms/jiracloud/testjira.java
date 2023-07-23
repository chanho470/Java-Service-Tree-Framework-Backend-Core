package com.arms.jiracloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;
@Component

public class testjira {

    @Value("${OAuth.cloudId}")
    private String cloudId;
    private static final String jiraBaseURL = "https://api.atlassian.com/ex/jira/fd024501-325e-45cb-9789-1c90fe504440";
    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;



    public testjira() {
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
    }

    public ResponseEntity getIssue(String issueId,String accessToken) {
        String url = jiraBaseURL + "issue/" + issueId;

        System.out.println("getIssue에서 accessToken:"+accessToken);

        // HttpHeaders에 Authorization 헤더 추가
        httpHeaders.setBearerAuth(accessToken);

        HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders); // HttpEntity의 Generic 타입 설정
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
    }

    public ResponseEntity createIssue(String key, String summary, String description, String issueType) {
        String createIssueJSON = createCreateIssueJSON(key, summary, description, issueType);

        String url = jiraBaseURL + "issue";

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<String>(createIssueJSON, httpHeaders);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

    }

    private String createCreateIssueJSON(String key, String summary, String description, String issueType) {
        String createIssueJSON = "{\"fields\":{\"project\":{\"key\":\"$KEY\"},\"summary\":\"$SUMMARY\",\"description\":\"$DESCRIPTION\",\"issuetype\": {\"name\": \"$ISSUETYPE\"}}}";

        createIssueJSON = createIssueJSON.replace("$KEY", key);
        createIssueJSON = createIssueJSON.replace("$SUMMARY", summary);
        createIssueJSON = createIssueJSON.replace("$DESCRIPTION", description);
        return createIssueJSON.replace("$ISSUETYPE", issueType);
    }

}