package com.arms.jiracloud;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;
@Component

public class testjira {

    private static final String username = "chanho9611@gmail.com";
    private static final String password = "ATATT3xFfGF0iohROaJjDtf-me3WtnFFjhwJAZJmNp4ricHtSQkJhoQ3quKSv615E-f__pVkO8jKGUVndcufhgmfdjkcTOdLafNIHABGCtyQp705Na9j4zS7Pw-iTXeo-81WsoWkHHk9dUu6DBMB4CGoZAAET_G2SUFKibgMVkB4U7oYaQQrUhs=EC1DE5C6";
    private static final String jiraBaseURL = "https://testchkim.atlassian.net/rest/api/3/";
    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;



    public testjira() {
        restTemplate = new RestTemplate();
        httpHeaders = createHeadersWithAuthentication();
    }

    private HttpHeaders createHeadersWithAuthentication() {
        String plainCreds = username + ":" + password;
        byte[] base64CredsBytes = Base64.getEncoder().encode(plainCreds.getBytes());
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        return headers;
    }

    public ResponseEntity getIssue(String issueId) {
        String url = jiraBaseURL + "issue/" + issueId;

        HttpEntity<?> requestEntity = new HttpEntity(httpHeaders);
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