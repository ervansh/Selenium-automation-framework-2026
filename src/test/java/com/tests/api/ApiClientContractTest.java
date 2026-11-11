package com.tests.api;

import com.framework.api.ApiClient;
import com.framework.api.ApiResponseAssertions;
import com.framework.config.FrameworkConfig;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.Test;

/** Optional smoke test. Configure api.base.uri and api.health.path for your target service. */
public class ApiClientContractTest {
    @Test(groups = {"api", "smoke"})
    public void healthEndpointShouldRespondSuccessfully() {
        FrameworkConfig config = FrameworkConfig.getInstance();
        String healthPath = config.get("api.health.path", "");
        if (config.get("api.base.uri", "").isBlank() || healthPath.isBlank()) {
            throw new SkipException("Configure api.base.uri and api.health.path to run the API smoke test.");
        }
        Response response = new ApiClient().get(healthPath);
        ApiResponseAssertions.assertStatusCode(response, config.getInt("api.health.expected.status", 200));
    }
}
