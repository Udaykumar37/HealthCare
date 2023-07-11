package org.healthcare.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.healthcare.user.exception.DuplicateUserNameException;
import org.healthcare.user.exception.NoSuchUserFoundException;
import org.healthcare.user.repository.entity.User;
import org.healthcare.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<?> getAllUsers() {
    log.info("Entered getAllUsers");
    List<User> userList = userService.getAllUsers();
    log.info("Exited getAllUsers");
    return new ResponseEntity<>(userList, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable("id") long id) {
    log.info("Entered gerUserById={}", id);
    Optional<User> user = userService.getUserById(id);
    log.info("Exited getUserById={}", id);
    if (user.isPresent()) {
      return new ResponseEntity<>(user, HttpStatus.OK);
    }
    throw new NoSuchUserFoundException("User details not found with given Id=" + id);
  }

  @GetMapping("check/{id}")
  public ResponseEntity<?> checkUserById(@PathVariable("id") long id) {
    log.info("Entered checkUserById={}", id);
    Optional<User> user = userService.getUserById(id);
    log.info("Exited checkUserById={}", id);
    return new ResponseEntity<>(user.isPresent(), HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<?> getUserByName(@PathVariable("name") String name) {
    log.info("Entered getUserByName={}", name);
    List<User> userList = userService.getUserByName(name);
    log.info("Exited getUserByName={}", name);
    return new ResponseEntity<>(userList, HttpStatus.OK);
  }

  @GetMapping("/username/{username}")
  public ResponseEntity<?> getUserByUserName(@PathVariable("username") String userName) {
    log.info("Entered getUserByUserName={}", userName);
    User user = userService.getUserByUserName(userName);
    if (user != null) {
      return new ResponseEntity<>(user, HttpStatus.OK);
    }
    throw new NoSuchUserFoundException("User details not found with given username=" + userName);
  }

  @PostMapping
  public ResponseEntity<?> addUser(@RequestBody User user) {
    log.info("Entered addUser");
    User userResponse = userService.addUser(user);
    if (userResponse != null) {
      log.info("Exited addUser, user added successfully with id={}", userResponse.getId());
      return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
    String message = "Account with username already exists";
    log.info("{}", message);
    throw new DuplicateUserNameException(message);
  }

}
