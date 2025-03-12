package com.radz.wfh.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AOPConfig {

  private static final String LOG_MESSAGE_FORMAT = "%s.%s execution time: %dms";

  @Pointcut(
      "execution(* com.radz.wfh.service.*.*(..)) || execution(* com.radz.wfh.controller.*.*(..)) || execution(* com.radz.wfh.repository.*.*(..))")
  public void isPublicMethod() {}

  @Before("isPublicMethod()")
  public void logBefore(JoinPoint joinPoint) {

    log.debug(
        "Method called: {} with args: {}",
        joinPoint.getSignature().toLongString(),
        joinPoint.getArgs());
  }

  @AfterReturning(pointcut = "isPublicMethod()", returning = "result")
  public void logAfterReturning(JoinPoint joinPoint, Object result) {
    log.debug("Method: {} returned: {}", joinPoint.getSignature().toShortString(), result);
  }
}
