# Architecture and engineering decisions

## Module boundaries
- **config**: immutable configuration snapshot; JVM properties > environment > optional classpath file > safe defaults.
- **core/driver**: browser construction and thread-scoped driver ownership.
- **web/pages**: Page Object Model primitives. Keep locators and page behavior out of tests.
- **api**: reusable REST Assured request specification; add service clients and DTOs as the API surface grows.
- **database**: JDBC connection boundary and parameterized query helpers.
- **logging**: Log4j2 logger factory and externalized configuration.
- **tests**: TestNG tests grouped by web, API and database concerns.

## Concurrency and lifecycle
A WebDriver is bound to a TestNG worker using ThreadLocal. Never share a driver between tests or threads. The owning test base always quits and removes the driver in an @AfterMethod(alwaysRun = true) hook. API and database tests do not create browsers.

Database connections are not stored globally. Each test opens and closes its own connection using try-with-resources. SQL values are bound through PreparedStatement; do not concatenate untrusted values into SQL.

## Configuration and secrets
Copy src/test/resources/config/config.example.properties to framework.properties for local use (the latter is ignored by Git), or provide environment variables / JVM properties. Do not commit credentials, tokens, or environment-specific endpoints. Environment names are derived by uppercasing keys and replacing dots/hyphens with underscores (db.username -> DB_USERNAME).

## Extension points
- Add browser capabilities in DriverFactory; use Selenium Grid by setting grid.url.
- Add page objects under web/pages and reusable widgets under web/components.
- Add API clients, request/response models and contract assertions under api.
- Add domain-specific repositories/query objects under database.
- Add TestNG data providers, retry policies and environment-specific suites only when a concrete use case requires them.
