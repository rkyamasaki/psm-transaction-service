# PSM Transaction Service

The application provides APIs for account creation and financial transaction processing including:

 - balance management 
 - idempotency validation
 - account transaction history

---

## Technologies

* Java 21
* Spring Boot 3
* Spring Data JPA
* PostgreSQL
* Flyway
* Gradle
* Docker Compose
* Caffeine Cache
* JUnit 5
* Mockito

---

## Pre-requisites

* Java 21 configured on `JAVA_HOME`
* Docker and Docker Compose installed (If you have installed Docker recently, Docker Compose is usually included by default)

## Running the Application

### Start PostgreSQL

```bash
docker compose up -d
```

### Portainer

Portainer is available to help viewing dockers containers of the project.

#### Access
Open:
* http://localhost:9000

On the first access, create your administrator account.

After login:

* Select Local Environment
* Access the container dashboard
* Monitor logs, volumes, networks and container status

### Run application local

```bash
./gradlew bootRun
```

Application will be available at:

```text
http://localhost:8081
```
---
## Running on docker container
This project has a specific directory if you want to execute the app on docker

You can see those files on `dockerized` directory

To run the app in docker just run this command:
```text
docker compose -f dockerized/docker-compose.yml up -d --build
```

To stop the container, just run this command:
```text
docker compose -f dockerized/docker-compose.yml down
```
Note: To avoid conflicts, make sure that no containers from a previous execution of this 
project are running before starting the environment.

Application will be available at:

```text
http://localhost:8081
```

Portainer will be available at:
```
http://localhost:9000
```

---

## API Examples

### Create Account

```http
POST /accounts
Content-Type: application/json
```

Request

```json
{
  "document_number": "12345678901"
}
```

Response

```json
{
  "account_id": 1,
  "document_number": "12345678901"
}
```

---

### Create Transaction

```http
POST /transactions
Idempotency-Key: 550e8400-e29b-41d4-a716-446655440000
Content-Type: application/json
```

Request

```json
{
  "account_id": 1,
  "operation_type_id": 4,
  "amount": 100.00
}
```

Response

```json
{
  "transaction_id": 1,
  "account_id": 1,
  "operation_type_id": 4,
  "amount": 100.00
}
```
---
## Postman Collections
You can import the Postman collection to help test the application.

The collection file `PSM-Transaction-service-tests.postman_collection.json`
is located in the `postman` directory.

The requests for the `/transactions` endpoint automatically 
generate a random Idempotency-Key and populate the request header before execution.

---
## API Documentation

Open Api is available at:
```text
http://localhost:8081/v3/api-docs
```

Swagger UI is available at:

```text
http://localhost:8081/swagger-ui.html
```

---
## Running Tests

```bash
./gradlew test
```

Generate build artifact:

```bash
./gradlew clean build
```
