package com.pf.selenium.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {
  private final By usernameInput = byTestId("username-input");
  private final By createUserButton = byTestId("create-user-btn");
  private final By refreshUsersButton = byTestId("refresh-users-btn");
  private final By resetUsersButton = byTestId("reset-users-btn");
  private final By usersList = byTestId("users-list");
  private final By usersCount = byTestId("users-count");
  private final By healthBadge = byTestId("health-badge");

  public HomePage(WebDriver driver) {
    super(driver);
  }

  public void open(String baseUrl) {
    driver.get(baseUrl);
    waitVisible(usernameInput);
  }

  public void resetUsers() {
    WebElement button = waitVisible(resetUsersButton);
    button.click();
    wait.until(d -> d.findElement(usersCount).getText().trim().equals("0 users"));
  }

  public void createUser(String username) {
    WebElement input = waitVisible(usernameInput);
    input.clear();
    input.sendKeys(username);
    driver.findElement(createUserButton).click();
  }

  public void refreshUsers() {
    driver.findElement(refreshUsersButton).click();
  }

  public void waitForUser(String username) {
    wait.until(d -> {
      String text = d.findElement(usersList).getText();
      return text.contains(username);
    });
  }

  public String usersListText() {
    return driver.findElement(usersList).getText();
  }

  public String healthText() {
    return driver.findElement(healthBadge).getText();
  }
}
