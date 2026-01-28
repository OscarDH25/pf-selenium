package com.pf.selenium.backend.controller;

import com.pf.selenium.backend.domain.User;
import com.pf.selenium.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserRepository repository;

  public UserController(UserRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public List<User> getAll() {
    return repository.findAll();
  }

  @PostMapping
  public User create(@RequestBody User user) {
    return repository.save(new User(user.getUsername()));
  }
}
