package com.pf.selenium.backend.controller;

import com.pf.selenium.backend.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Profile({"docker", "test"})
@RestController
@RequestMapping("/test")
public class TestController {

  private final UserRepository repository;

  public TestController(UserRepository repository) {
    this.repository = repository;
  }

  @PostMapping("/reset")
  public ResponseEntity<Map<String, String>> reset() {
    repository.deleteAll();
    return ResponseEntity.ok(Map.of(
        "status", "ok",
        "message", "All users deleted"
    ));
  }
}
