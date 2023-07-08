package org.healthcare.order.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.healthcare.order.exception.InvalidMedicineIdException;
import org.healthcare.order.exception.InvalidUserIdException;
import org.healthcare.order.properties.ClientProperties;
import org.healthcare.order.repository.OrderRepository;
import org.healthcare.order.repository.entity.Order;
import org.healthcare.order.service.dto.MedicineDetails;
import org.healthcare.order.service.dto.OrderDetails;
import org.healthcare.order.service.dto.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final ClientProperties clientProperties;
  private final RestTemplate restTemplate;

  public Order createOrder(Order order) {
    boolean isValidUser = this.isValidUser(order.getUserId());
    boolean isValidMedicine = this.isValidMedicine(order.getMedicineId());
    if (!isValidUser) {
      throw new InvalidUserIdException("No User found with customerId=" + order.getUserId());
    }
    if (!isValidMedicine) {
      throw new InvalidMedicineIdException(
          "No Medicine found with medicineId=" + order.getMedicineId());
    }
    return orderRepository.saveAndFlush(order);
  }

  public Optional<Order> getOrderById(Long orderId) {
    log.info("Entered getOrderById={}", orderId);
    return orderRepository.findById(orderId);
  }

  public OrderDetails getOrderDetails(long orderId) {
    log.info("Entered getOrderDetails={}", orderId);
    Optional<Order> orderOptional = this.getOrderById(orderId);
    if (orderOptional.isEmpty()) {
      return OrderDetails.builder().build();
    }
    Order order = orderOptional.get();
    UserDetails userDetails = this.getUserDetailsById(order.getUserId());
    MedicineDetails medicineDetails = this.getMedicineDetailsById(order.getMedicineId());
    return OrderDetails.builder().id(order.getId()).userDetails(userDetails)
        .medicineDetails(medicineDetails).noOfItems(order.getNoOfItems()).build();
  }

  private boolean isValidMedicine(Long medicineId) {
    String baseUrl = clientProperties.getPharmacyClient().getBaseUrl();
    String completeUrl = baseUrl + "/medicine/check/" + medicineId;
    Boolean response = restTemplate.getForObject(completeUrl, Boolean.class);
    return response != null && response;
  }

  private boolean isValidUser(Long userId) {
    String baseUrl = clientProperties.getUserClient().getBaseUrl();
    String completeUrl = baseUrl + "/user/check/" + userId;
    Boolean response = restTemplate.getForObject(completeUrl, Boolean.class);
    return response != null && response;
  }

  private MedicineDetails getMedicineDetailsById(Long medicineId) {
    String baseUrl = clientProperties.getPharmacyClient().getBaseUrl();
    String completeUrl = baseUrl + "/medicine/" + medicineId;
    MedicineDetails response = restTemplate.getForObject(completeUrl, MedicineDetails.class);
    return response;
  }

  private UserDetails getUserDetailsById(Long userId) {
    String baseUrl = clientProperties.getUserClient().getBaseUrl();
    String completeUrl = baseUrl + "/user/" + userId;
    UserDetails response = restTemplate.getForObject(completeUrl, UserDetails.class);
    return response;
  }
}
