package com.framework.api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import static io.restassured.RestAssured.given;

/**
 * Stateless REST client. A fresh request specification is created per call to avoid
 * sharing mutable REST Assured request state between parallel tests.
 */
public final class ApiClient {
    public Response get(String path) { return request().when().get(path); }
    public Response get(String path, Map<String, ?> queryParameters) {
        return request().queryParams(queryParameters).when().get(path);
    }
    public Response post(String path, Object body) { return request().body(body).when().post(path); }
    public Response put(String path, Object body) { return request().body(body).when().put(path); }
    public Response patch(String path, Object body) { return request().body(body).when().patch(path); }
    public Response delete(String path) { return request().when().delete(path); }
    public Response delete(String path, Object body) { return request().body(body).when().delete(path); }
    private RequestSpecification request() {
        return given().spec(RequestSpecFactory.defaultSpecification());
    }
}
