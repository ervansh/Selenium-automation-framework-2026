package com.framework.api;

import com.framework.config.FrameworkConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/** Creates a reusable REST Assured request specification for the configured service. */
public final class RequestSpecFactory {
    private RequestSpecFactory() { }

    public static RequestSpecification defaultSpecification() {
        String baseUri = FrameworkConfig.getInstance().get("api.base.uri", "");
        if (baseUri.isBlank()) throw new IllegalStateException("Set api.base.uri before executing API tests.");
        return new RequestSpecBuilder().setBaseUri(baseUri)
                .setContentType(ContentType.JSON).setAccept(ContentType.JSON).build();
    }
}