package com.pf.selenium.api;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HealthApiTest extends BaseApiTest {

  @Test
  void healthReturnsOk() {
    given()
      .when()
      .get("/health")
      .then()
      .statusCode(200)
      .body(equalTo("OK"));
  }
}
