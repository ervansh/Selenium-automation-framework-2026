# Architecture and engineering decisions

## Module boundaries
- **config**: immutable configuration snapshot; JVM properties > environment > optional classpath file > safe defaults.
- **core/driver**: browser construction and thread-scoped driver ownership.
- **web/pages**: Page Object Model primitives. Keep locators and page behavior out of tests.
- **web/actions**: reusable low-level browser interactions.
- **web/waits**: centralized explicit-wait policy.
- **api**: stateless REST Assured client and request specification factory.
- **database**: JDBC connection boundary, parameterized query helpers and explicit transaction scope.
- **logging**: Log4j2 logger factory and externalized configuration.
- **tests**: TestNG tests grouped by web, API and database concerns.

## Concurrency and lifecycle
A WebDriver is bound to a TestNG worker using ThreadLocal. Never share a driver between tests or threads. The owning test base always quits and removes the driver in an @AfterMethod(alwaysRun = true) hook. API and database tests do not create browsers.

ApiClient creates a fresh RequestSpecification per call and does not retain mutable request state. Database connections are not stored globally. Each test opens and closes its own connection using try-with-resources. SQL values are bound through PreparedStatement; do not concatenate untrusted values into SQL.

## Configuration and secrets
Copy src/test/resources/config/config.example.properties to framework.properties for local use (the latter is ignored by Git), or provide environment variables / JVM properties. Do not commit credentials, tokens, or environment-specific endpoints. Environment names are derived by uppercasing keys and replacing dots/hyphens with underscores (db.username -> DB_USERNAME).

## Extension points
- Add browser capabilities in DriverFactory; use Selenium Grid by setting grid.url.
- Add page objects under web/pages and reusable widgets under web/components.
- Add application-independent browser interactions under web/actions.
- Add service-specific API clients and DTOs under api.
- Add domain-specific repositories/query objects under database.
- Add TestNG data providers and environment-specific suites when concrete use cases require them.
