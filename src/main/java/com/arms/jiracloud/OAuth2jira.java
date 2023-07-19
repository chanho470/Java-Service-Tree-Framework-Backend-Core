package com.arms.jiracloud;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OAuth2jira {
    private RestTemplate restTemplate;

    private static final String autorizationUrl = "https://auth.atlassian.com/authorize?audience=api.atlassian.com&client_id=mf9ZDzYl5r0LqO2r4ojp7LLL9EGRKrGa&scope=read%3Ajira-work%20manage%3Ajira-project%20write%3Ajira-work&redirect_uri=http%3A%2F%2Flocalhost%3A31313%2Farms%2FjiraCloud%2FCallback&state=chkim&response_type=code&prompt=consent";
    public OAuth2jira() {
        restTemplate = new RestTemplate();
        //httpHeaders = requestautorizationUrl();
    }

//    public String requestautorizationUrl(){
//        ResponseEntity<String> response = restTemplate.exchange(autorizationUrl, HttpMethod.GET, null, String.class);
//        System.out.println("인증 URL 리턴22222222"+response);
//        String responseBody = response.getBody();
//        return responseBody;
//    }

    public ResponseEntity requestautorizationUrl(){
        // restTemplate.exchange(autorizationUrl, HttpMethod.GET, null, String.class);

        System.out.println("인증 URL 리턴22222222"+restTemplate.exchange(autorizationUrl, HttpMethod.GET, null, String.class));
        return  restTemplate.exchange(autorizationUrl, HttpMethod.GET, null, String.class);
    }
}
