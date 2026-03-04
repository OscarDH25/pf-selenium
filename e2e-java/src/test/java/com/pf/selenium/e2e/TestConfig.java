package com.pf.selenium.e2e;

public class TestConfig {
  private final String baseUrl;
  private final String seleniumUrl;

  public TestConfig() {
    this.baseUrl = getEnvOrDefault("E2E_BASE_URL", "http://host.docker.internal:8080");
    this.seleniumUrl = getEnvOrDefault("E2E_SELENIUM_URL", "http://localhost:4444/wd/hub");
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public String getSeleniumUrl() {
    return seleniumUrl;
  }

  private String getEnvOrDefault(String key, String defaultValue) {
    String value = System.getenv(key);
    if (value == null || value.isBlank()) {
      return defaultValue;
    }
    return value;
  }
}
