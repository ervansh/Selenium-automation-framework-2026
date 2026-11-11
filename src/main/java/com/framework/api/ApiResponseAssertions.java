package com.framework.api;

import io.restassured.response.Response;

/** Common response assertions without coupling production framework code to a test runner. */
public final class ApiResponseAssertions {
    private ApiResponseAssertions() { }

    public static void assertStatusCode(Response response, int expectedStatusCode) {
        if (response == null) throw new AssertionError("API response must not be null.");
        if (response.statusCode() != expectedStatusCode) {
            throw new AssertionError("Expected HTTP status " + expectedStatusCode + " but received "
                    + response.statusCode() + ". Response body: " + response.asString());
        }
    }

    public static void assertContentTypeContains(Response response, String expectedContentType) {
        if (response == null) throw new AssertionError("API response must not be null.");
        String actualContentType = response.getContentType();
        if (actualContentType == null || !actualContentType.contains(expectedContentType)) {
            throw new AssertionError("Expected content type to contain '" + expectedContentType
                    + "' but was '" + actualContentType + "'.");
        }
    }
}
