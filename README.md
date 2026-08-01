# Banking System

A backend banking system REST API built with Spring Boot, developed as a hands-on project to learn Spring Boot fundamentals while producing a portfolio-ready application.

## Overview

This project implements core banking operations — user management, account management, and fund transfers — with a focus on clean layered architecture, schema-managed migrations, transactional integrity, and JWT-based authentication.

## Tech Stack

- **Java 17** / **Spring Boot** 4.1.0
- **Spring Data JPA** / **Hibernate** 7.4.1
- **Spring Security** with **JWT** (jjwt) authentication
- **MySQL** 8.0
- **Flyway** — database schema managed exclusively through versioned migrations
- **Lombok**
- **HikariCP** — connection pooling
- **springdoc-openapi (Swagger UI)** — interactive API documentation
- **Docker** / **Docker Compose** — containerized app + database
- **Maven**

## Architecture

The project follows a standard layered architecture:

```
Controller → Service → Repository → Entity
```

- **Entities** map directly to database tables, with relationships (`@ManyToOne`, `@ManyToMany`) reflecting the schema.
- **Repositories** use Spring Data JPA for persistence.
- **Services** contain all business logic — validation rules, default values, and transactional operations (e.g. fund transfers) live here, not in the database.
- **Controllers** expose REST endpoints and delegate to services.
- **DTOs** (Java `records`) decouple the API contract from internal entities and carry Jakarta Validation constraints.
- **GlobalExceptionHandler** (`@RestControllerAdvice`) converts business exceptions into clean, consistent HTTP responses (404, 409, 400) instead of raw stack traces.

Schema is fully managed by Flyway migrations (`ddl-auto: validate`), so the application never generates or alters schema itself.

## Modules

| Module | Description |
|---|---|
| User | Registration (via `/api/auth/register`), retrieval, deletion |
| Account | Linked to User, Currency, and AccountType; explicit validation, default currency logic |
| Currency / AccountType / TransactionType | Read-only lookup endpoints, seeded via Flyway |
| Transfer | Two-step flow: create (PENDING) → process (atomic, double-entry bookkeeping) |
| Transaction | Debit/Credit records generated as a side effect of transfer processing |
| Auth / Security | JWT-based authentication, BCrypt password hashing, role-based access (`roles` / `user_roles` many-to-many) |

## Business Rules

A few notable design decisions enforced at the service layer:

- **Account currency** defaults to a standard currency (IRR) if not explicitly provided.
- **Account type** has no default — it must be explicitly supplied by the client, or an error is thrown. This is an intentional constraint.
- **Fund transfers** follow a two-step process: `createTransfer` (creates a PENDING record) and `processTransfer` (`@Transactional`, atomically moves balances and creates paired DEBIT/CREDIT transaction records). Insufficient balance or an already-processed transfer is rejected.
- **Transfers cannot be deleted** — financial records are immutable, matching real-world audit requirements.

## Database

- Database: `banking_system`
- Migrations live in `src/main/resources/db/migration/`, applied incrementally (`V1` through `V8` and beyond) — schema and seed data both evolve through version-controlled SQL files, never manual edits.
- Database credentials are injected via the `${DB_PASSWORD}` environment variable — no secrets are committed to the repository.

## Security

- Passwords are hashed with **BCrypt** before storage — plaintext passwords are never persisted.
- Authentication is **stateless**, using **JWT** tokens (`Authorization: Bearer <token>`).
- `POST /api/auth/register` and `POST /api/auth/login` are public; all other endpoints require a valid token.
- Roles (`CUSTOMER`, `ADMIN`) are modeled as a many-to-many relationship (`roles` + `user_roles` tables), allowing a user to hold multiple roles.
- Swagger UI supports testing authenticated endpoints via the **Authorize** button.

## API Documentation

Once the application is running, interactive API documentation is available at:

```
http://localhost:8080/swagger-ui/index.html
```

## Running the Project

### Option 1 — Docker (recommended, no local setup needed)

**Prerequisites:** Docker Desktop

```bash
git clone https://github.com/Smard-dev/banking-system.git
cd banking-system

# create a .env file with:
# DB_PASSWORD=your_password_here

docker compose up --build
```

The API will be available at `http://localhost:8080`, MySQL at `localhost:3307`.

### Option 2 — Local (IntelliJ / Maven)

**Prerequisites:** Java 17+, Maven, MySQL 8.0+

```bash
git clone https://github.com/Smard-dev/banking-system.git
cd banking-system

export DB_PASSWORD=your_password_here

# Flyway migrations run automatically on startup
./mvnw spring-boot:run
```

## API Testing

HTTP request files for manual API testing are in `test_http_requests/`:
- `0-seed.http` — seed data for a fresh test run (creates a base user)
- `user.http` — user endpoint requests
- `account.http` — account endpoint requests
- `transfer.http` — full transfer flow (create → process → verify balances)

These use IntelliJ's built-in HTTP Client and `client.global.set()` to chain requests without manual copy-pasting of IDs.

## Roadmap

- [ ] Expand Swagger annotations (`@Operation`, `@Tag`, `@ApiResponse`) across all controllers
- [ ] Automated test suite (unit tests for services, integration tests for the transfer flow)
- [ ] Broader DTO/Validation coverage across all write endpoints

## About

<img width="730" height="782" alt="image" src="https://github.com/user-attachments/assets/f1fd53ad-08b3-44d2-b78b-1093822db972" />

<img width="1128" height="787" alt="image" src="https://github.com/user-attachments/assets/240fb990-5663-4f95-90e2-a85b4e570503" />


