# Enterprise extension guidance

## Design boundaries

- Page Objects own page locators and user-visible workflows. Tests should assert business outcomes, not duplicate low-level Selenium calls.
- WebActions provides reusable low-level interactions. Use explicit waits; do not add fixed sleeps or mix implicit and explicit waits.
- ApiClient is stateless and creates a fresh REST Assured request specification for every call. Service-specific clients should compose it rather than adding endpoint details to the shared framework.
- DatabaseTransaction provides an explicit transaction boundary. A transaction is rolled back when closed without a successful commit.
- DatabaseClient returns a connection owned by the caller. Use try-with-resources for both connection and transaction scopes.

## Parallel execution rules

1. A WebDriver belongs to one TestNG worker thread only.
2. Do not store WebDriver, mutable RequestSpecification, JDBC Connection, or test data in static mutable fields.
3. API clients may be shared only while remaining stateless.
4. Each database test should acquire its own connection or transaction.
5. Prefer parallel="tests" or parallel="classes" until the tests and fixtures are proven safe for method-level parallelism.

## Reliability and observability

- Use explicit waits around asynchronous browser state; avoid Thread.sleep.
- Keep request/response logging disabled by default in CI because payloads and headers may contain secrets or personal data.
- Attach screenshots only on failed UI tests and do not allow diagnostic failures to mask the original test failure.
- Add retries only for explicitly identified transient infrastructure failures. Do not retry assertion failures by default, because doing so hides product defects and flaky behavior.

## Project-specific extension sequence

1. Add service-specific API clients and request/response models.
2. Add domain-oriented database repositories and migration/seed strategy for the target application.
3. Add test-data builders and cleanup fixtures.
4. Add environment-specific TestNG suite files and tags.
5. Add static analysis, dependency vulnerability scanning, and quality gates to CI.
6. Add Selenium Grid or containerized browser execution after defining the supported browser matrix.
