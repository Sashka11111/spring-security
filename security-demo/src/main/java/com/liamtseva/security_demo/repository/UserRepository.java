package com.liamtseva.security_demo.repository;

import com.liamtseva.security_demo.entity.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByUsername(String username);
}