package com.radz.wfh.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.radz.wfh.dto.EmployeeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class Auth0Service {

  private final RestTemplate restTemplate;

  @Value("${backend.client.id}")
  private String clientId;

  @Value("${backend.client.secret}")
  private String clientSecret;

  @Value("${backend.audience}")
  private String audience;

  @Value("${backend.grant-type}")
  private String grantType;

  @Value("${backend.token.url}")
  private String backendTokenUrl;

  @Value("${user.info.url}")
  private String userInfoUrl;

  public Auth0Service(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public EmployeeInfo getUserInfo(String token) throws JsonProcessingException {
    ResponseEntity<String> responseEntity =
        restTemplate.exchange(userInfoUrl, HttpMethod.GET, getHttpEntity(token), String.class);
    String responseBody = responseEntity.getBody();
    assert responseBody != null;
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(responseBody, EmployeeInfo.class);
  }

  private HttpEntity<MultiValueMap<String, String>> getHttpEntity(String token) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("content-type", "application/json");
    httpHeaders.add("Authorization", "Bearer " + token);
    return new HttpEntity<>(httpHeaders);
  }
}
