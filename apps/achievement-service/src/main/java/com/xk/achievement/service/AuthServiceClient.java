package com.xk.achievement.service;

import com.xk.achievement.dto.TokenRequest;
import com.xk.achievement.dto.TokenResponse;
import com.xk.achievement.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthServiceClient {

    private final RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    public AuthServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TokenResponse authenticate(String username, String password) {
        TokenRequest request = new TokenRequest("password", username, password, clientId, clientSecret);
        return restTemplate.postForObject(userServiceUrl + "/users/token", request, TokenResponse.class);
    }

    public UserDTO getUserMe(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(userServiceUrl + "/users/me", HttpMethod.GET, entity, UserDTO.class).getBody();
    }

    public UserDTO register(String username, String password, String userType) {
        UserDTO request = new UserDTO(null, username, password, userType);
        return restTemplate.postForObject(userServiceUrl + "/users/register", request, UserDTO.class);
    }
}
