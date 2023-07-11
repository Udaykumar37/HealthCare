package org.healthcare.order.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("external-client")
public class ClientProperties {

  private UserClient userClient;
  private PharmacyClient pharmacyClient;

  @Getter
  @Setter
  public static class UserClient {
    private String baseUrl;
  }

  @Getter
  @Setter
  public static class PharmacyClient {
    private String baseUrl;
  }
}
