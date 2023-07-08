package org.healthcare.order.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.healthcare.order.exception.NoSuchOrderFoundException;
import org.healthcare.order.repository.entity.Order;
import org.healthcare.order.service.OrderService;
import org.healthcare.order.service.dto.OrderDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("order")
@AllArgsConstructor
@Slf4j
public class OrderController {

  private final OrderService orderService;

  @GetMapping("/{id}")
  public ResponseEntity<?> getOrderById(@PathVariable Long id) {
    log.info("Entered getOrderById={}", id);
    Optional<Order> order = orderService.getOrderById(id);
    log.info("Exited getOrderById={}", id);
    if (order.isPresent()) {
      return ResponseEntity.of(order);
    }
    throw new NoSuchOrderFoundException("No Order found with orderId=" + id);
  }

  @GetMapping("/details/{id}")
  public ResponseEntity<?> getOrderDetails(@PathVariable Long id) {
    log.info("Entered getOrderDetails={}", id);
    OrderDetails orderDetails = orderService.getOrderDetails(id);
    log.info("Exited getOrderDetails={}", id);
    return new ResponseEntity<>(orderDetails, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> createOrder(@RequestBody Order order) {
    log.info("Entered createOrder");
    Order orderResponse = orderService.createOrder(order);
    log.info("Exited createOrder");
    return new ResponseEntity<>(order, HttpStatus.OK);
  }
}
