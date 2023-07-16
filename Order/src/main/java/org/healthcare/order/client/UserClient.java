package org.healthcare.order.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.healthcare.order.properties.ClientProperties;
import org.healthcare.order.service.dto.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Component
@AllArgsConstructor
@Slf4j
public class UserClient {

  private final RestTemplate restTemplate;
  private final ClientProperties clientProperties;

  public boolean isValidUser(Long userId) {
    String baseUrl = clientProperties.getUserClient().getBaseUrl();
    String completeUrl = baseUrl + "/user/check/" + userId;
    Boolean response = restTemplate.getForObject(completeUrl, Boolean.class);
    return response != null && response;
  }

  @CircuitBreaker(name = "userService", fallbackMethod = "fallbackMethodGetUserDetailsById")
  @TimeLimiter(name = "userService")
  @Retry(name = "userService")
  public CompletableFuture<UserDetails> getUserDetailsById(Long userId) {
    String baseUrl = clientProperties.getUserClient().getBaseUrl();
    String completeUrl = baseUrl + "/user/" + userId;
    return CompletableFuture.supplyAsync(
        () -> restTemplate.getForObject(completeUrl, UserDetails.class));
  }

  public CompletableFuture<UserDetails> fallbackMethodGetUserDetailsById(Long userId,
      Exception exception) {
    log.error("fallbackMethodGetUserDetailsById={} exception={}", userId, exception.getMessage(),
        exception);
    return CompletableFuture.supplyAsync(UserDetails::new);
  }
}
