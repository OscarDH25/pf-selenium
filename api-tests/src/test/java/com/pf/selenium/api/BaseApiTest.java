package com.pf.selenium.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;

import static io.restassured.RestAssured.given;

public abstract class BaseApiTest {

  @BeforeEach
  void configure() {
    ApiConfig config = new ApiConfig();
    RestAssured.baseURI = config.getBaseUrl();
    resetUsers();
  }

  protected void resetUsers() {
    given()
      .when()
      .post("/test/reset")
      .then()
      .statusCode(200);
  }

  protected String createUser(String username) {
    return given()
      .contentType(ContentType.JSON)
      .body("{\"username\":\"" + username + "\"}")
      .when()
      .post("/users")
      .then()
      .statusCode(201)
      .extract()
      .path("username");
  }
}
