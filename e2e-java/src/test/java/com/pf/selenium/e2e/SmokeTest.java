package com.pf.selenium.e2e;

import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SmokeTest {
  private WebDriver driver;
  private TestConfig config;

  @BeforeEach
  void setUp() throws Exception {
    config = new TestConfig();
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    driver = new RemoteWebDriver(new java.net.URL(config.getSeleniumUrl()), options);
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
  }

  @AfterEach
  void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Test
  void createAndListUser() {
    HomePage home = new HomePage(driver);
    home.open(config.getBaseUrl());

    home.resetUsers();

    String username = "e2e-user-" + System.currentTimeMillis();
    home.createUser(username);
    home.refreshUsers();

    home.waitForUser(username);

    String healthText = home.healthText().toLowerCase();
    assertTrue(healthText.contains("ok"), "Health badge is not OK");
  }
}
