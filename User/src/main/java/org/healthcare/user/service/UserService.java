package org.healthcare.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.healthcare.user.repository.UserRepository;
import org.healthcare.user.repository.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public List<User> getAllUsers() {
    log.info("Entered getAllUsers");
    return userRepository.findAll();
  }

  public Optional<User> getUserById(long id) {
    log.info("Entered getUserById={}", id);
    return userRepository.findById(id);
  }

  public List<User> getUserByName(String name) {
    log.info("Entered getUserByName={}", name);
    return userRepository.findByName(name);
  }

  public User getUserByUserName(String userName) {
    log.info("Entered getUserByUserName={}", userName);
    return userRepository.findByUserName(userName);
  }

  public User addUser(User user) {
    log.info("Entered addUser");
    User existingUser = this.getUserByUserName(user.getUserName());
    if (existingUser != null) {
      return null;
    }
    return userRepository.saveAndFlush(user);
  }
}
