package org.healthcare.order.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.healthcare.order.properties.ClientProperties;
import org.healthcare.order.service.dto.MedicineDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Component
@AllArgsConstructor
@Slf4j
public class PharmacyClient {

  private final RestTemplate restTemplate;
  private final ClientProperties clientProperties;


  public boolean isValidMedicine(Long medicineId) {
    String baseUrl = clientProperties.getPharmacyClient().getBaseUrl();
    String completeUrl = baseUrl + "/medicine/check/" + medicineId;
    Boolean response = restTemplate.getForObject(completeUrl, Boolean.class);
    return response != null && response;
  }

  @CircuitBreaker(name = "pharmacyService", fallbackMethod = "fallbackMethodGetMedicineDetailsById")
  @TimeLimiter(name = "pharmacyService")
  @Retry(name = "pharmacyService")
  public CompletableFuture<MedicineDetails> getMedicineDetailsById(Long medicineId) {
    String baseUrl = clientProperties.getPharmacyClient().getBaseUrl();
    String completeUrl = baseUrl + "/medicine/" + medicineId;
    return CompletableFuture.supplyAsync(
        () -> restTemplate.getForObject(completeUrl, MedicineDetails.class));
  }

  public CompletableFuture<MedicineDetails> fallbackMethodGetMedicineDetailsById(Long medicineId,
      Exception exception) {
    log.error("fallbackMethodGetMedicineDetailsById={} exception={}", medicineId,
        exception.getMessage(), exception);
    return CompletableFuture.supplyAsync(MedicineDetails::new);
  }
}
