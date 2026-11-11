package com.tests.api;

import com.framework.api.RequestSpecFactory;
import com.tests.base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class ExampleApiTest extends BaseTest {
    @Test
    public void healthEndpointShouldRespondSuccessfully() {
        String path = System.getProperty("api.health.path", "");
        if (path.isBlank()) throw new SkipException("Configure -Dapi.health.path to run the API health check.");
        Response response = given().spec(RequestSpecFactory.defaultSpecification()).when().get(path);
        Assert.assertTrue(response.statusCode() >= 200 && response.statusCode() < 300,
                "Expected a successful HTTP response but got " + response.statusCode());
    }
}