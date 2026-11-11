# Selenium Automation Framework

A modular Java test automation framework covering **Web UI, REST API and PostgreSQL database testing**. The repository uses Maven and TestNG, with Allure reporting and Log4j2 logging.

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

Dependency versions are pinned in `pom.xml` and should be upgraded deliberately with CI verification.

## Project structure

```text
src/
  main/
    java/com/framework/
      config/                 # Central configuration
      core/driver/            # DriverFactory and thread-scoped DriverManager
      web/pages/              # Page Object base
      api/                    # REST Assured request specification
      database/               # JDBC connection and query helpers
      logging/                # Logger factory
    resources/
      log4j2.xml
  test/
    java/com/tests/
      base/                   # Common and browser test lifecycle
      web/                    # Web UI tests
      api/                    # API tests
      database/               # PostgreSQL tests
      listeners/              # Allure failure attachments
    resources/
      config/config.example.properties
      suites/testng.xml
docs/
  architecture.md
.github/workflows/
  maven.yml
```

## Prerequisites

- JDK 21
- Maven 3.9+
- Chrome, Firefox or Microsoft Edge for local UI execution
- PostgreSQL for database tests
- Selenium Grid URL only when remote execution is required

Selenium Manager handles local browser driver resolution. For Grid, configure `grid.url`.

## Configure

Copy `src/test/resources/config/config.example.properties` to `src/test/resources/config/framework.properties` and set the target application/API/database values. The local file is ignored by Git.

Configuration precedence is:
1. JVM system property
2. Environment variable
3. `config/framework.properties`
4. Safe default

Examples of environment variable names: `WEB_BASE_URL`, `API_BASE_URI`, `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `DB_TESTS_ENABLED`.

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

Enable API health check after setting `api.base.uri` and the relative health path:

```bash
mvn clean verify -Dapi.base.uri=https://your-api-host -Dapi.health.path=/health
```

Enable PostgreSQL connectivity test:

```bash
mvn clean verify -Ddb.url=jdbc:postgresql://localhost:5432/automation -Ddb.username=automation -Ddb.password=secret -Ddb.tests.enabled=true
```

Unconfigured example tests skip rather than calling a guessed application or service. Configure the target system before enabling them.

## Reports and logs

- Allure result files: `target/allure-results` (when the TestNG Allure adapter is active).
- Log output: console and rolling files under `logs/`.
- Generate/view an Allure report using the Allure Report CLI installed in your environment.

## Engineering principles

- One WebDriver per TestNG worker thread; never share browser sessions across parallel tests.
- Browser setup and teardown are isolated to web tests; API/database tests do not launch browsers.
- JDBC connections and statements use try-with-resources.
- SQL values use PreparedStatement parameters.
- Configuration and secrets are externalized.
- Page Objects, service clients and database query objects should encapsulate application-specific behavior.

See [docs/architecture.md](docs/architecture.md) for design boundaries and extension points.
