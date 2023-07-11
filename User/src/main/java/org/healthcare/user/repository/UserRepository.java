package org.healthcare.user.repository;

import org.healthcare.user.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByName(String name);

  User findByUserName(String userName);
}
