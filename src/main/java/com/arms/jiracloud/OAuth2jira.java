package com.arms.jiracloud;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OAuth2jira {
    private RestTemplate restTemplate;

    private static final String autorizationUrl = "https://auth.atlassian.com/authorize?audience=api.atlassian.com&client_id=mf9ZDzYl5r0LqO2r4ojp7LLL9EGRKrGa&scope=offline_access%20read%3Ajira-work%20manage%3Ajira-project%20write%3Ajira-work&redirect_uri=http%3A%2F%2Flocalhost%3A31313%2Farms%2FjiraCloud%2FCallback&state=chkim&response_type=code&prompt=consent";
    public OAuth2jira() {
        restTemplate = new RestTemplate();
        //httpHeaders = requestautorizationUrl();
    }



    public ResponseEntity requestautorizationUrl(){
        // restTemplate.exchange(autorizationUrl, HttpMethod.GET, null, String.class);

        System.out.println("인증 URL 리턴22222222"+restTemplate.exchange(autorizationUrl, HttpMethod.GET, null, String.class));
        return  restTemplate.exchange(autorizationUrl, HttpMethod.GET, null, String.class);
    }

    @Value("${OAuth.clientId}")
    private String clientId;
    @Value("${OAuth.clientSecret}")
    private  String clientSecret;

    private static final String grantType = "authorization_code";
    private static final String redirectUri = "http://localhost:31313/arms/jiraCloud/Callback";

    public String GetToken(String code){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"grant_type\": \"" + grantType + "\",\"client_id\": \"" + clientId + "\",\"client_secret\": \"" + clientSecret + "\",\"code\": \"" + code + "\",\"redirect_uri\": \"" + redirectUri + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        String url = "https://auth.atlassian.com/oauth/token";
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        String response = responseEntity.getBody();

        try {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        String accessToken = jsonNode.get("access_token").asText();

            System.out.println("토큰 응답 데이터 :"+accessToken);
            return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // 토큰 획득에 실패한 경우 null
    }
}
