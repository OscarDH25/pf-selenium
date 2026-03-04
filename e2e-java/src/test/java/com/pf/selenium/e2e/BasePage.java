package com.pf.selenium.e2e;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
  protected final WebDriver driver;
  protected final WebDriverWait wait;

  public BasePage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  protected By byTestId(String testId) {
    return By.cssSelector("[data-testid=\"" + testId + "\"]");
  }

  protected WebElement waitVisible(By locator) {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }
}
