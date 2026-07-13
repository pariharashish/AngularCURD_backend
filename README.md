# AngularCURD Backend

A Spring Boot REST API backend for Employee Management with Angular frontend integration.

## Technology Stack

- **Backend Framework:** Spring Boot 3.5.7
- **Language:** Java 17
- **Database:** MySQL (JPA/Hibernate ORM)
- **API Documentation:** Swagger/OpenAPI 3.0
- **Build Tool:** Gradle
- **Docker:** Containerized deployment

## Features

- ✅ Complete CRUD operations for Employee Management
- ✅ Department and Department Type management
- ✅ RESTful API with proper error handling
- ✅ CORS enabled for Angular frontend
- ✅ OpenAPI/Swagger documentation
- ✅ Custom actuator endpoints
- ✅ Global exception handling
- ✅ Comprehensive unit tests

## API Endpoints

### Employee Management
- `GET /api/employees` - Get all employees
- `GET /api/employees/{id}` - Get employee by ID
- `POST /api/employees` - Create new employee
- `PATCH /api/employees/{id}` - Update employee
- `DELETE /api/employees/{id}` - Delete employee

### Department Management
- `GET /api/departments` - Get all departments
- `POST /api/departments` - Create new department
- `PATCH /api/departments/{id}` - Update department

### Actuator Endpoints
- `GET /actuator/health` - Health check
- `GET /actuator/applicationInfo` - Application information
- `GET /actuator/customEndpoint` - Custom endpoint

## Project Structure

```
src/
├── main/
│   ├── java/com/AngularCURD/
│   │   ├── controller/          # REST controllers
│   │   ├── service/             # Business logic
│   │   ├── entity/              # JPA entities
│   │   ├── repository/          # Data access layer
│   │   ├── dto/                 # Request/Response DTOs
│   │   ├── exceptions/          # Custom exceptions & handlers
│   │   ├── config/              # Spring configurations
│   │   └── customActuator/      # Custom actuator endpoints
│   └── resources/
│       └── application*.properties  # Configuration files
└── test/
    └── java/com/AngularCURD/    # Unit tests
```

## Getting Started

### Prerequisites
- Java 17+
- MySQL 5.7+
- Gradle 8.0+
- Docker (optional)

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/pariharashish/AngularCURD_backend.git
   cd AngularCURD_backend
   ```

2. **Configure database:**
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/angular_curd
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Build the project:**
   ```bash
   ./gradlew clean build
   ```

4. **Run the application:**
   ```bash
   ./gradlew bootRun
   ```
   
   The API will be available at `http://localhost:8080`

### Running Tests

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests EmployeeServiceTest

# Run with coverage
./gradlew test jacocoTestReport
```

## Docker Deployment

1. **Build Docker image:**
   ```bash
   ./gradlew bootBuildImage
   ```

2. **Run Docker container:**
   ```bash
   docker run -p 8080:8080 \
     -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/angular_curd \
     -e SPRING_DATASOURCE_USERNAME=root \
     -e SPRING_DATASOURCE_PASSWORD=password \
     angularcurd_backend:latest
   ```

## API Documentation

Swagger UI is available at: `http://localhost:8080/swagger-ui.html`

OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Configuration

### CORS Configuration

Edit `application.properties`:
```properties
app.cors.origins=http://localhost:4200
```

### Database Profiles

- **Development:** `application.properties`
- **Docker:** `application-docker.properties`

## Error Handling

The API uses standard HTTP status codes:
- `200 OK` - Successful GET/POST/PUT
- `201 Created` - Resource created
- `204 No Content` - Resource deleted
- `400 Bad Request` - Invalid input
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

## Recent Fixes (v1.0.1)

- ✅ Fixed exception handling for RuntimeException
- ✅ Added null checks to prevent NPE in updates
- ✅ Moved CORS configuration from hardcoded to properties
- ✅ Fixed HTTP status codes in delete operations
- ✅ Fixed validation annotations usage
- ✅ Added health check to Docker container
- ✅ Added comprehensive unit test suite

## Contributing

Please follow these guidelines:
1. Create a feature branch
2. Make your changes
3. Add/update tests
4. Submit a pull request

## License

MIT License

## Support

For issues, please create an issue on GitHub.
