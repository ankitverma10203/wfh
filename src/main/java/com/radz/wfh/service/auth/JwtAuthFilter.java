package com.radz.wfh.service.auth;

import com.radz.wfh.dto.EmployeeInfo;
import com.radz.wfh.repository.EmployeeDetailRepository;
import com.radz.wfh.service.EmployeeRegistrationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Service
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
  private final Auth0Service auth0Service;
  private final EmployeeDetailRepository employeeDetailRepository;
  private final EmployeeRegistrationService employeeRegistrationService;

  public JwtAuthFilter(
      Auth0Service auth0Service,
      EmployeeDetailRepository employeeDetailRepository,
      EmployeeRegistrationService employeeRegistrationService) {
    this.auth0Service = auth0Service;
    this.employeeDetailRepository = employeeDetailRepository;
    this.employeeRegistrationService = employeeRegistrationService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = getBearerToken(request);

    if (StringUtils.isNotBlank(token)) {
      try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isRegisteredUser = employeeDetailRepository.existsById(authentication.getName());

        if (!isRegisteredUser) {
          EmployeeInfo employeeInfo = auth0Service.getUserInfo(token);
          employeeRegistrationService.register(employeeInfo);
        }
      } catch (Exception e) {
        log.error(e.getMessage());
      }
    }
    filterChain.doFilter(request, response);
  }

  public static String getBearerToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }

    return null;
  }
}
