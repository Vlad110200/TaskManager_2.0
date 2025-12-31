# Task Manager API
This is a RESTful Task Manager application built with Spring Boot 3, featuring JWT-based authentication and full CRUD functionality for tasks.
## Features
Task CRUD
- Create a task (POST /tasks)
- Get all tasks (GET /tasks)
- Get a task by ID (GET /tasks/{id})
- Update a task (PUT /tasks/{id})
- Delete a task (DELETE /tasks/{id})

JWT Authentication
- All endpoints are secured with JWT tokens.
- Provides token validation for authorized access.

Validation
- Input fields validated using @Valid, @NotBlank, and @Size.
- Errors handled via ValidationExceptionHandler.

Pagination
- GET /tasks supports page and size query parameters.

DataBase
- Tasks and user data are stored in a relational database using Spring Data JPA.
- Database persists all application data.
- Supports H2 (in-memory) for development and testing, and can be configured for PostgreSQL, MySQL, or other relational databases.
- Database schema is automatically created and updated via JPA/Hibernate.

Unit & Integration Tests
- Controller tests using MockMvc.
- Integration tests with full Spring context.
- Tests cover:
- Successful operations (create, update, delete, get)

## Project Structure
```text
src/main/java/vlad/taskmanager
├─ app                 # Main application class
├─ config              # Spring & security configs
├─ controller          # REST controllers
├─ dto                 # Data Transfer Objects
├─ exception           # Exception handling
├─ repository          # JPA repositories
├─ security            # JWT utilities and filters
├─ service             # Business logic

src/test/java/vlad/taskmanager
├─ config              # Test-specific configs
├─ controller          # Controller tests
├─ service             # Service tests
```
## Running the project
```
mvn spring-boot:run
```
Server runs at http://localhost:8082.

Swagger UI available at http://localhost:8082/swagger-ui/index.html

## API Usage Example

- Create Task: POST /tasks with JSON body { "title": "Task", "description": "Desc" } → 201 Created
- Get Task: GET /tasks/{id} → 200 OK
- Update Task: PUT /tasks/{id} → 200 OK
- Delete Task: DELETE /tasks/{id} → 204 No Content
- Get Tasks List: GET /tasks?page=0&size=10 → 200 OK

All requests require JWT Authorization header:
```
Authorization: Bearer <token>
```

## Testing
Run tests
```
mvn test
```

