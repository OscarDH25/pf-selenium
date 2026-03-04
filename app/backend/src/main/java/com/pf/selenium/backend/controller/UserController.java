package com.pf.selenium.backend.controller;

import com.pf.selenium.backend.domain.User;
import com.pf.selenium.backend.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<User> create(@Valid @RequestBody User user) {
    User created = repository.save(new User(user.getUsername()));
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }
}
