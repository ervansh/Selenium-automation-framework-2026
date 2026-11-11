package com.framework.api;

import com.framework.config.FrameworkConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/** Creates a fresh reusable REST Assured request specification for each API call. */
public final class RequestSpecFactory {
    private RequestSpecFactory() { }

    public static RequestSpecification defaultSpecification() {
        FrameworkConfig config = FrameworkConfig.getInstance();
        String baseUri = config.get("api.base.uri", "");
        if (baseUri.isBlank()) throw new IllegalStateException("Set api.base.uri before executing API tests.");

        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON);
        String bearerToken = config.get("api.auth.token", "");
        if (!bearerToken.isBlank()) builder.addHeader("Authorization", "Bearer " + bearerToken);
        return builder.build();
    }
}
