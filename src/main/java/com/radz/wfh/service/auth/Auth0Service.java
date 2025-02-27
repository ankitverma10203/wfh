package com.radz.wfh.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.radz.wfh.dto.EmployeeInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
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

  public String getAccessToken() {
    ResponseEntity<String> responseEntity =
        restTemplate.exchange(
            backendTokenUrl, HttpMethod.POST, getHttpEntityForBackendToken(), String.class);

    String responseBody = responseEntity.getBody();
    JSONObject jsonObject = new JSONObject(responseBody);
    return jsonObject.getString("access_token");
  }

  public List<String> getRoles(String token, String id) {
    ResponseEntity<String> responseEntity =
        restTemplate.exchange(
            "https://dev-38ur00tkntqcylhl.us.auth0.com/api/v2/users/" + id + "/roles",
            HttpMethod.GET,
            getHttpEntity(token),
            String.class);

    List<String> roles = new ArrayList<>();

    JSONArray rolesArray = new JSONArray(responseEntity.getBody());
    rolesArray.forEach(
        role -> {
          JSONObject roleDetail = new JSONObject(String.valueOf(role));
          roles.add(String.valueOf(roleDetail.get("name")));
        });
    return roles;
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
    httpHeaders.add("Authorization", "Bearer " + token);
    return new HttpEntity<>(httpHeaders);
  }

  private HttpEntity<String> getHttpEntityForBackendToken() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("content-type", "application/json");

    JSONObject body = new JSONObject();
    body.put("client_id", this.clientId);
    body.put("client_secret", this.clientSecret);
    body.put("audience", this.audience);
    body.put("grant_type", this.grantType);
    return new HttpEntity<>(body.toString(), httpHeaders);
  }
}
