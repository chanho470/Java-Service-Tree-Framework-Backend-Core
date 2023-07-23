package com.arms.jiracloud;
import com.arms.jiracloud.service.TokenRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OAuth2jira {
    private RestTemplate restTemplate;
    private static  String grantType;
    private static final String redirectUri = "http://localhost:31313/arms/jiraCloud/callback";

    private static final String  oauthUrl = "https://auth.atlassian.com/oauth/token";
    @Value("${OAuth.clientId}")
    private String clientId;
    @Value("${OAuth.clientSecret}")
    private  String clientSecret;

    public OAuth2jira() {
        restTemplate = new RestTemplate();
    }


    public String GetToken(String code){
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@ GetToken @@@@@@@@@@@@@@@@@@@@@@@");
        RestTemplate restTemplate = new RestTemplate();
        grantType = "authorization_code";
        // header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // body 설정
        TokenRequest requestBody = new TokenRequest();
        requestBody.setGrant_type(grantType);
        requestBody.setClient_id(clientId);
        requestBody.setClient_secret(clientSecret);
        requestBody.setCode(code);
        requestBody.setRedirect_uri(redirectUri);
        HttpEntity<TokenRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        // 데이터 전송
        ResponseEntity<String> responseEntity = restTemplate.exchange(oauthUrl, HttpMethod.POST, requestEntity, String.class);
        String response = responseEntity.getBody();
        System.out.println("리턴 값 :"+response);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            String accessToken = jsonNode.get("access_token").asText();

            System.out.println("접근 토큰 값 :"+accessToken);

            refreshToken(accessToken); // refresh token get

            return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // 토큰 획득에 실패한 경우 null
    }


    public String refreshToken(String accessToken){

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@ refreshToken @@@@@@@@@@@@@@@@@@@@@@@");
        RestTemplate restTemplate = new RestTemplate();

        // header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        grantType =  "refresh_token";

        // body 설정
        TokenRequest requestBody = new TokenRequest();
        requestBody.setGrant_type(grantType);
        requestBody.setClient_id(clientId);
        requestBody.setClient_secret(clientSecret);
        requestBody.setRefresh_token(accessToken);
        HttpEntity<TokenRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        // 데이터 전송
        ResponseEntity<String> responseEntity = restTemplate.exchange(oauthUrl, HttpMethod.POST, requestEntity, String.class);
        String response = responseEntity.getBody();
        System.out.println("리턴 값 :"+response);


        return response;
    }

}
