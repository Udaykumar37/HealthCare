package org.healthcare.order.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.healthcare.order.client.PharmacyClient;
import org.healthcare.order.client.UserClient;
import org.healthcare.order.exception.InvalidMedicineIdException;
import org.healthcare.order.exception.InvalidUserIdException;
import org.healthcare.order.repository.OrderRepository;
import org.healthcare.order.repository.entity.Order;
import org.healthcare.order.service.dto.MedicineDetails;
import org.healthcare.order.service.dto.OrderDetails;
import org.healthcare.order.service.dto.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final PharmacyClient pharmacyClient;
  private final UserClient userClient;

  public Order createOrder(Order order) {
    boolean isValidUser = userClient.isValidUser(order.getUserId());
    boolean isValidMedicine = pharmacyClient.isValidMedicine(order.getMedicineId());
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

  public List<Order> getAllOrders() {
    log.info("Entered getAllOrders");
    return orderRepository.findAll();
  }

  /*
  * When using Resilience4j's @CircuitBreaker annotation, the method annotated with @CircuitBreaker
  * and the caller method should be in separate classes.
  * */
  public OrderDetails getOrderDetails(long orderId) {
    log.info("Entered getOrderDetails={}", orderId);
    Optional<Order> orderOptional = this.getOrderById(orderId);
    if (orderOptional.isEmpty()) {
      return OrderDetails.builder().build();
    }
    Order order = orderOptional.get();
    UserDetails userDetails = userClient.getUserDetailsById(order.getUserId()).join();
    MedicineDetails medicineDetails =
        pharmacyClient.getMedicineDetailsById(order.getMedicineId()).join();
    return OrderDetails.builder().id(order.getId()).userDetails(userDetails)
        .medicineDetails(medicineDetails).noOfItems(order.getNoOfItems()).build();
  }
}
