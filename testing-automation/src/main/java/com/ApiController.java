package com;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class ApiController {
    private String baseUrl = "https://my-json-server.typicode.com/fvo111/api-home-test";

    public Response apiUpdateUser(String requestBody){
        Response response = given().log().all().baseUri(baseUrl).basePath("/")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("user_history");

        response.getBody().prettyPrint();
        return response;
    }

    public Response apiGetCheckoutTransactions() {
        Response response = given().log().all().baseUri(baseUrl).basePath("/")
                .contentType(ContentType.JSON)
                .get("checkout_transaction");

        response.getBody().prettyPrint();
        return response;
    }

    public Response apiGetUserHistory() {
        Response response = given().log().all().baseUri(baseUrl).basePath("/")
                .contentType(ContentType.JSON)
                .get("user_history");

        response.getBody().prettyPrint();
        return response;
    }
}
