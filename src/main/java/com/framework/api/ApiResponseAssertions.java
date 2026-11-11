package com.framework.api;

import io.restassured.response.Response;
import org.testng.Assert;

/** Common response assertions with readable failure messages. */
public final class ApiResponseAssertions {
    private ApiResponseAssertions() { }
    public static void assertStatusCode(Response response, int expectedStatusCode) {
        Assert.assertNotNull(response, "API response must not be null.");
        Assert.assertEquals(response.statusCode(), expectedStatusCode,
                "Unexpected HTTP status. Response body: " + response.asString());
    }
    public static void assertContentTypeContains(Response response, String expectedContentType) {
        Assert.assertNotNull(response, "API response must not be null.");
        Assert.assertTrue(response.getContentType() != null
                        && response.getContentType().contains(expectedContentType),
                "Expected content type to contain '" + expectedContentType
                        + "' but was '" + response.getContentType() + "'.");
    }
}
