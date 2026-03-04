package com.pf.selenium.api;

import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class UsersApiTest extends BaseApiTest {

  @Test
  void createUserAndList() {
    String username = "api-user-" + System.currentTimeMillis();
    createUser(username);

    List<String> usernames = given()
      .when()
      .get("/users")
      .then()
      .statusCode(200)
      .extract()
      .jsonPath()
      .getList("username");

    assertThat(usernames, hasItem(username));
  }

  @Test
  void createUserRejectsBlankUsername() {
    given()
      .contentType(ContentType.JSON)
      .body("{\"username\":\"\"}")
      .when()
      .post("/users")
      .then()
      .statusCode(400);
  }
}
