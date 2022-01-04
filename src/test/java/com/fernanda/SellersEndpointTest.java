package com.fernanda;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@QuarkusTest
public class SellersEndpointTest {

    @Test
    public void testGetSeller() {
        Response response = given()
                .when()
                .get("/sellers/1")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        assertThat(response.jsonPath().getString("name"), containsString("Adam"));
    }

    @Test
    public void testCreateSeller() {
        given()
                .when()
                .body("{\"name\" : \"Charlotte\"}")
                .contentType("application/json")
                .post("/sellers")
                .then()
                .statusCode(201)
                .body(
                        containsString("\"id\":"),
                        containsString("\"name\":\"Charlotte\"")
                );

        Response response = given()
                .when()
                .get("/sellers/4")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        assertThat(response.jsonPath().getString("name"), containsString("Charlotte"));
    }

    @Test
    public void testUpdateSeller() {
        given()
                .when()
                .body("{\"name\" : \"Ava\"}")
                .contentType("application/json")
                .put("/sellers/2")
                .then()
                .statusCode(200)
                .body(
                        containsString("\"id\":"),
                        containsString("\"name\":\"Ava\"")
                );

        Response response = given()
                .when()
                .get("/sellers/2")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        assertThat(response.jsonPath().getString("name"), containsString("Ava"));
    }

    @Test
    public void testDeleteSeller() {
        given()
                .when()
                .delete("/sellers/2")
                .then()
                .statusCode(204);

        given()
                .when()
                .get("/sellers/2")
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetNotFound() {
        given()
                .when()
                .get("/sellers/200")
                .then()
                .statusCode(404);
    }

    @Test
    public void testEntityNotFoundForDelete() {
        given()
                .when()
                .delete("/sellers/100")
                .then()
                .statusCode(404)
                .body(emptyString());
    }

    @Test
    public void testEntityNotFoundForUpdate() {
        given()
                .when()
                .body("{\"name\" : \"Charles\"}")
                .contentType("application/json")
                .put("/fruits/100")
                .then()
                .statusCode(404)
                .body(emptyString());
    }
}
