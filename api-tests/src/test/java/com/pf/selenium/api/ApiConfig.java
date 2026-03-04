package com.pf.selenium.api;

public class ApiConfig {
  private final String baseUrl;

  public ApiConfig() {
    this.baseUrl = getEnvOrDefault("API_BASE_URL", "http://localhost:8080/api");
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  private String getEnvOrDefault(String key, String defaultValue) {
    String value = System.getenv(key);
    if (value == null || value.isBlank()) {
      return defaultValue;
    }
    return value;
  }
}
