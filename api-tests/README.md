# API Tests (RestAssured + JUnit)

This module provides API-level tests for the pf-selenium backend.

## Requirements
- Java 17+
- Docker (for the stack)

## Run tests (wrapper)
```powershell
.\api-tests\mvnw.cmd test
```

## Run tests (Maven installed)
```bash
mvn -f api-tests/pom.xml test
```

## Configuration
Environment variables:
- `API_BASE_URL` (default: `http://localhost:8080/api`)

## Coverage
- Health endpoint
- Users CRUD (create + list)
- Validation (blank username rejected)
