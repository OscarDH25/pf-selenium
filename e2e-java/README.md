# Java E2E (Selenium + JUnit)

This module provides a minimal Java Selenium framework for the pf-selenium UI.

## Requirements
- Java 17+
- Docker (for the stack)

## Run tests (wrapper)
```powershell
.\e2e-java\mvnw.cmd test
```

## Run tests (Maven installed)
```bash
mvn -f e2e-java/pom.xml test
```

## Configuration
Environment variables:
- `E2E_BASE_URL` (default: `http://host.docker.internal:8080`)
- `E2E_SELENIUM_URL` (default: `http://localhost:4444/wd/hub`)

## Structure
- `BasePage` -> shared helpers
- `HomePage` -> page object for the main UI
- `SmokeTest` -> end-to-end smoke test
