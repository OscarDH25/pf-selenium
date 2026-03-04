# pf-selenium

QA portfolio project focused on testability and automation.

## Goals
- Full-stack app designed for testability (data-testid, deterministic data, reset endpoint)
- E2E automation with Selenium (smoke flow)
- Dockerized stack for reproducible runs

## Architecture
- Nginx serves the frontend and proxies `/api/*` to the backend
- Spring Boot backend with JPA + PostgreSQL
- Selenium container for browser automation

## Project structure
- `app` -> backend + frontend + nginx config
- `e2e` -> Selenium smoke test (Node)

## Run locally (Docker)
```bash
docker compose up --build
```

UI and API:
- UI: http://localhost:8080
- API: http://localhost:8080/api/*

## Test endpoints (docker/test profile)
- `POST /api/test/reset` -> deletes all users (used by E2E to keep tests deterministic)

## E2E (Node + Selenium)
```bash
cd e2e
npm install
npm test
```

The test connects to the Selenium container at `http://localhost:4444` and opens the UI at `http://host.docker.internal:8080`.
