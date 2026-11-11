# Enterprise Test Automation Framework

A modular Java automation framework for **Web UI, REST API and PostgreSQL database testing**. It uses Maven and TestNG, with Allure reporting and Log4j2 logging.

## Technology baseline

| Area | Technology |
|---|---|
| Language/runtime | Java 21 |
| Build | Maven |
| Web automation | Selenium 4 (Chrome, Firefox, Edge; local or Selenium Grid) |
| Test runner | TestNG |
| API automation | REST Assured |
| Database | JDBC / PostgreSQL |
| Reporting | Allure TestNG |
| Logging | Log4j2 |
| Serialization | Jackson |

Dependency versions are pinned in pom.xml. Upgrade them deliberately and verify through CI.

## Project structure

```text
src/
  main/java/com/framework/
    config/                 # Central configuration
    core/driver/            # DriverFactory and thread-scoped DriverManager
    web/pages/              # Page Object base
    web/actions/            # Reusable browser interactions
    web/waits/              # Central explicit-wait policy
    api/                    # Request specification, API client and assertions
    database/               # JDBC connections, query helpers and transactions
    logging/                # Logger factory
  main/resources/
    log4j2.xml
  test/java/com/tests/
    base/                   # Common and browser test lifecycle
    web/                    # Web UI tests
    api/                    # API tests
    database/               # PostgreSQL tests
    listeners/              # Allure failure attachments
  test/resources/
    config/config.example.properties
    suites/testng.xml
docs/
  architecture.md
  enterprise-design.md
.github/workflows/
  maven.yml
```

## Prerequisites

- JDK 21
- Maven 3.9+
- Chrome, Firefox or Microsoft Edge for local UI execution
- PostgreSQL for database tests
- Selenium Grid URL only when remote execution is required

Selenium Manager handles local browser driver resolution. For Grid, configure grid.url.

## Configure

Copy src/test/resources/config/config.example.properties to src/test/resources/config/framework.properties and set the target application/API/database values. The local file is ignored by Git.

Configuration precedence:
1. JVM system property
2. Environment variable
3. config/framework.properties
4. Safe default

Examples: WEB_BASE_URL, API_BASE_URI, API_AUTH_TOKEN, DB_URL, DB_USERNAME, DB_PASSWORD, DB_TESTS_ENABLED.

Never commit passwords, tokens, or environment-specific endpoints.

## Run

Compile and execute the TestNG suite:

```bash
mvn clean verify
```

Run with a selected browser:

```bash
mvn clean verify -Dbrowser=firefox -Dbrowser.headless=true
```

Run against Selenium Grid:

```bash
mvn clean verify -Dbrowser=chrome -Dgrid.url=http://localhost:4444
```

Run the API health smoke test after configuring api.base.uri and api.health.path:

```bash
mvn clean verify -Dapi.base.uri=https://your-api-host -Dapi.health.path=/health
```

Enable PostgreSQL connectivity test:

```bash
mvn clean verify -Ddb.url=jdbc:postgresql://localhost:5432/automation -Ddb.username=automation -Ddb.password=secret -Ddb.tests.enabled=true
```

Unconfigured example tests skip rather than calling a guessed application or service.

## Reports and logs

- Allure result files: target/allure-results.
- Log output: console and rolling files under logs/.
- Generate/view an Allure report using the Allure Report CLI installed in your environment.

## Engineering principles

- One WebDriver per TestNG worker thread; never share browser sessions across parallel tests.
- Browser setup and teardown are isolated to web tests; API/database tests do not launch browsers.
- API clients create a fresh request specification per call and do not retain mutable request state.
- JDBC connections and statements use try-with-resources; transaction scopes roll back if not committed.
- SQL values use PreparedStatement parameters.
- Configuration and secrets are externalized.
- Page Objects, service clients and database query objects encapsulate application-specific behavior.
- Explicit waits are preferred over fixed sleeps; implicit waits should remain disabled.

See docs/architecture.md and docs/enterprise-design.md for design boundaries and extension guidance.
