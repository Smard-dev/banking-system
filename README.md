# Banking System

A backend banking system built with Spring Boot, developed as a hands-on project to learn Spring Boot fundamentals while producing a portfolio-ready application.

## Overview

This project implements core banking operations — user management, account management, and fund transfers — with a focus on clean layered architecture, schema-managed migrations, and correct transactional behavior.

## Tech Stack

- **Java** / **Spring Boot** 4.1.0
- **Spring Data JPA** / **Hibernate** 7.4.1
- **MySQL** 8.0.45
- **Flyway** 12.4.0 — database schema managed exclusively through versioned migrations
- **Lombok**
- **HikariCP** — connection pooling
- **Maven**

## Architecture

The project follows a standard layered architecture:

```
Controller → Service → Repository → Entity
```

- **Entities** map directly to database tables, with relationships (e.g. `Account` → `User` via `@ManyToOne`) reflecting the schema.
- **Repositories** use Spring Data JPA for persistence.
- **Services** contain all business logic — validation rules and default values (such as default account currency) live here, not in the database.
- **Controllers** expose REST endpoints.

Schema is fully managed by Flyway migrations (`ddl-auto: validate`), so the application never generates or alters schema itself.

## Modules

| Module | Status | Description |
|---|---|---|
| User | ✅ Complete | Full CRUD (Entity, Repository, Service, Controller) |
| Account | ✅ Complete | Linked to User, Currency, and AccountType; explicit validation, default currency logic |
| Currency / AccountType | ✅ Complete | Read-only lookup endpoints |
| Transfer | 🚧 In Progress | Fund transfers between accounts with atomicity/transaction handling |
| Validation / DTO layer | 📋 Planned | Request validation and DTO mapping |
| Security | 📋 Planned | Authentication and authorization |

## Database

- Database: `banking_system`
- Migrations: `V1__` base schema, `V2__` seed data for lookup tables (currencies, account types), with further versions added incrementally as features are built.
- Database credentials are injected via the `${DB_PASSWORD}` environment variable — no secrets are committed to the repository.

## Business Rules

A few notable design decisions enforced at the service layer:

- **Account currency** defaults to a standard currency if not explicitly provided.
- **Account type** has no default — it must be explicitly supplied by the client, or an `IllegalStateException` is thrown. This is an intentional constraint, not an oversight.

## API Testing

HTTP request files for manual API testing are in `test_http_requests/`:
- `0-seed.http` — seed data for a fresh test run
- `user.http` — user endpoint requests
- `account.http` — account endpoint requests

## Getting Started

### Prerequisites
- Java 17+ (or your configured JDK version)
- Maven
- MySQL 8.0+

### Setup

```bash
# clone the repository
git clone https://github.com/Smard-dev/banking-system.git
cd banking-system

# set the database password
export DB_PASSWORD=your_password_here

# create the database
mysql -u root -p -e "CREATE DATABASE banking_system"

# run the application (Flyway migrations run automatically on startup)
./mvnw spring-boot:run
```

## Roadmap

- [ ] Complete Transfer module (transaction linkage, atomicity)
- [ ] Add DTO and validation layer
- [ ] Implement Spring Security
- [ ] Expand test coverage

## About

This project is being built as a learning exercise in Spring Boot and as a portfolio piece for freelance and backend developer roles.
