# BooksManagement

A Spring Boot-based backend application for managing books, authors, categories, PDF files, book thumbnails, pricing, downloads, authentication, and related operations.

The application is designed using a layered architecture with Spring Boot, Spring Data JPA, PostgreSQL, Spring Security, JWT authentication, and PDF processing.

---

## 1. Technology Stack

| Technology | Version / Details |
|---|---|
| Java | 25 |
| Spring Boot | 4.0.6 |
| Spring Framework | Managed by Spring Boot |
| Spring Data JPA | Spring Boot managed |
| Hibernate | 7.2.12.Final |
| Database | PostgreSQL 18.3 |
| PostgreSQL Driver | Spring Boot managed |
| PDF Processing | Apache PDFBox 3.0.3 |
| Authentication | Spring Security + JWT |
| JWT Library | JJWT 0.12.5 |
| Object Mapping | ModelMapper 3.2.0 |
| Validation | Spring Boot Validation |
| Mail | Spring Boot Starter Mail |
| Build Tool | Maven |
| Testing | JUnit 5 / Spring Boot Test |

---

## 2. Project Overview

BooksManagement provides backend APIs for managing books and their related information.

The application currently supports functionality around:

- Book management
- Author management
- Book category management
- PDF file upload
- PDF file storage
- PDF metadata management
- First-page thumbnail generation
- Book pricing
- Download count tracking
- User authentication and authorization
- JWT-based security
- Validation
- Email functionality

---

## 3. High-Level Architecture

```text
                    Client
                      |
                      v
              REST API / Controller
                      |
                      v
                Service Layer
                      |
          +-----------+-----------+
          |                       |
          v                       v
    Repository Layer        PDF Processing
          |                       |
          v                       v
     PostgreSQL              Apache PDFBox
```

The application follows a layered architecture:

```text
Controller
    |
    v
Service
    |
    v
Repository
    |
    v
PostgreSQL
```

Supporting components such as authentication, validation, PDF processing, and email are handled by their respective application services.

---

# 4. Project Structure

A recommended project structure is:

```text
src/
└── main/
    ├── java/
    │   └── com/
    │       └── dileep/
    │           └── ecommerce/
    │               └── ms/
    │
    │                   ├── controller/
    │                   │
    │                   ├── service/
    │                   │
    │                   ├── repository/
    │                   │
    │                   ├── entity/
    │                   │
    │                   ├── dto/
    │                   │
    │                   ├── security/
    │                   │
    │                   ├── config/
    │                   │
    │                   ├── exception/
    │                   │
    │                   └── util/
    │
    └── resources/
        ├── application.properties
        └── ...
```

The exact package structure may vary depending on the modules currently implemented in the project.

---

# 5. Main Domain Entities

## Book

The `BookEntity` represents a book in the system.

Current fields include:

```text
id
name
publishedOn
author
category
description
numberOfDownloads
file
fileName
fileType
path
thumbnail
price
```

### Relationships

```text
Book
 |
 +---- Author
 |
 +---- BookCategory
```

The book has:

- One author
- One category
- PDF file information
- Thumbnail information
- Pricing information
- Download information

---

# 6. PDF Upload

The application supports uploading PDF files associated with books.

The uploaded PDF can be represented using:

```java
private byte[] file;
```

along with metadata:

```java
private String fileName;
private String fileType;
private String path;
```

For the current implementation, PDF content can be stored as binary data.

For larger production deployments, object storage such as Amazon S3, Azure Blob Storage, or MinIO can be considered instead of storing large files directly inside PostgreSQL.

---

# 7. PDF Thumbnail Generation

The application uses Apache PDFBox to extract the first page of a PDF and generate a thumbnail.

The processing flow is:

```text
PDF Upload
     |
     v
Load PDF
     |
     v
Render First Page
     |
     v
BufferedImage
     |
     v
Resize Image
     |
     v
PNG Thumbnail
```

The current implementation uses:

```text
PDFBox: 3.0.3
```

Example processing:

```java
BufferedImage firstPage =
        pdfService.extractFirstPage(pdfBytes);

byte[] thumbnail =
        thumbnailService.createThumbnail(firstPage);
```

---

# 8. Thumbnail Generation

The thumbnail is generated from the first page of the uploaded PDF.

Current thumbnail dimensions:

```text
Width  : 200px
Height : 280px
Format : PNG
```

The thumbnail can then be associated with the book.

Example:

```java
book.setThumbnail(thumbnail);
```

---

# 9. Database

The application uses:

```text
PostgreSQL 18.3
```

The PostgreSQL database stores application data such as:

- Books
- Authors
- Categories
- Book metadata
- File information
- Thumbnail information
- Pricing information
- Other application data

---

# 10. Database Configuration

Configure PostgreSQL connection information in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/books_management
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

Do not commit production database credentials into source control.

For production environments, credentials should be provided through environment variables or a dedicated secrets-management solution.

---

# 11. JPA / Hibernate

The application uses:

```text
Spring Data JPA
        |
        v
Hibernate ORM
        |
        v
PostgreSQL
```

Repositories should extend Spring Data interfaces where appropriate.

Example:

```java
public interface BookRepository
        extends JpaRepository<BookEntity, Long> {
}
```

Business logic should remain in the service layer rather than repositories or controllers.

---

# 12. Security

The application uses:

```text
Spring Security
+
JWT
```

JWT dependencies:

```text
jjwt-api    0.12.5
jjwt-impl   0.12.5
jjwt-jackson 0.12.5
```

Authentication flow:

```text
Client
  |
  | Login
  v
Authentication API
  |
  v
Validate Credentials
  |
  v
Generate JWT
  |
  v
Return Token
  |
  v
Client
```

For protected endpoints:

```text
Client
  |
  | Authorization: Bearer <JWT>
  v
Spring Security
  |
  v
JWT Validation
  |
  v
Controller
```

---

# 13. Validation

The application uses Spring Boot Validation.

Dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

Request DTOs should use validation annotations such as:

```java
@NotBlank
private String name;

@NotNull
private Long categoryId;
```

Validation errors should be handled centrally rather than individually inside every controller.

---

# 14. Exception Handling

Application-specific exceptions should be handled through centralized exception handling.

Recommended structure:

```text
exception/
├── GlobalExceptionHandler
├── ResourceNotFoundException
├── BadRequestException
└── ...
```

This allows APIs to return consistent error responses.

Example response:

```json
{
  "timestamp": "2026-08-18T18:30:00",
  "status": 404,
  "message": "Book not found",
  "path": "/api/books/10"
}
```

---

# 15. DTO Usage

Controllers should preferably work with DTOs instead of exposing JPA entities directly.

Recommended flow:

```text
HTTP Request
     |
     v
Request DTO
     |
     v
Service
     |
     v
Entity
     |
     v
Repository
```

Response:

```text
Entity
   |
   v
Service
   |
   v
Response DTO
   |
   v
Controller
   |
   v
HTTP Response
```

This prevents persistence-layer implementation details from leaking into the API.

---

# 16. ModelMapper

The project currently uses:

```text
ModelMapper 3.2.0
```

It can be used to convert between DTOs and entities where the mapping is straightforward.

For complex mappings, explicit mapping is preferred because it makes the transformation easier to understand and maintain.

---

# 17. Email Support

The application includes:

```xml
spring-boot-starter-mail
```

This allows the application to send email notifications.

Email configuration should be provided through environment-specific configuration.

Example:

```properties
spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
```

Credentials should never be hardcoded in the source code.

---

# 18. ONNX Runtime

The project currently includes ONNX Runtime:

```text
com.microsoft.onnxruntime:onnxruntime:1.22.0
```

ONNX Runtime is used for local machine-learning model execution.

The model files are maintained separately from the Java application logic.

Model-specific processing should remain isolated from the core book-management business logic.

---

# 19. PDF Processing Components

PDF-related processing should remain separated from business logic.

Recommended structure:

```text
pdf/
├── PdfService
└── ThumbnailService
```

### PdfService

Responsible for:

- Loading PDF documents
- Extracting PDF pages
- Rendering PDF pages

### ThumbnailService

Responsible for:

- Resizing images
- Creating thumbnails
- Encoding thumbnails

This separation follows the Single Responsibility Principle.

---

# 20. Book Upload Flow

The current book upload process can be represented as:

```text
Client
  |
  | Upload Book + PDF
  v
Book Controller
  |
  v
Book Service
  |
  +----------------------+
  |                      |
  v                      v
Save Book Metadata    PDF Processing
                           |
                           v
                    Extract First Page
                           |
                           v
                    Generate Thumbnail
                           |
                           v
                    Save Book
                           |
                           v
                       PostgreSQL
```

---

# 21. API Design Guidelines

REST APIs should follow resource-oriented naming.

Recommended:

```text
GET    /api/books
GET    /api/books/{id}
POST   /api/books
PUT    /api/books/{id}
DELETE /api/books/{id}
```

Avoid action-oriented URLs where a resource-oriented design is possible.

For example, prefer:

```text
POST /api/books
```

over:

```text
POST /api/createBook
```

---

# 22. File Upload Considerations

For PDF uploads, validate:

- File presence
- File type
- File extension
- File size
- PDF validity

Do not rely only on the filename extension.

For example:

```text
book.pdf
```

does not guarantee that the uploaded content is actually a PDF.

Production systems should validate the actual file content as well.

---

# 23. Security Considerations for File Upload

Uploaded files are untrusted input.

The application should consider:

- Maximum upload size
- Allowed content types
- File signature validation
- Malicious file detection
- Path traversal prevention
- Safe file names
- Storage outside the executable/application directory
- Authorization before accessing private files

Never construct filesystem paths directly from an untrusted filename.

For example, avoid:

```java
Path.of(uploadDirectory, file.getOriginalFilename());
```

without sanitization and validation.

---

# 24. Production Storage Recommendation

The current implementation supports storing file data in the database.

For a production system with large numbers of books or large PDF files, consider:

```text
Application
    |
    v
Object Storage
    |
    +---- PDF
    +---- Thumbnail
```

Examples:

```text
Amazon S3
Azure Blob Storage
MinIO
Google Cloud Storage
```

The database can then store:

```text
filePath
fileName
fileType
fileSize
storageKey
```

instead of storing large binary files directly in PostgreSQL.

---

# 25. Performance Considerations

Important considerations for production:

### Database

- Add indexes to frequently searched columns.
- Avoid unnecessary eager relationships.
- Monitor generated SQL.
- Avoid N+1 queries.
- Use pagination for book listing APIs.

### File Processing

PDF rendering is CPU and memory intensive.

Avoid processing extremely large PDFs synchronously on request threads in high-traffic environments.

For large-scale systems, consider:

```text
Upload
  |
  v
Store File
  |
  v
Queue / Event
  |
  v
Background Processor
  |
  v
Generate Thumbnail
```

This keeps API response times predictable.

---

# 26. Pagination

For book listing APIs, avoid returning every book at once.

Use pagination:

```text
GET /api/books?page=0&size=20
```

Spring Data provides:

```java
Page<BookEntity>
```

which can be used for paginated queries.

This becomes important when the number of books grows significantly.

---

# 27. Logging

Use structured application logging instead of:

```java
System.out.println(...)
```

Prefer:

```java
private static final Logger log =
        LoggerFactory.getLogger(BookService.class);

log.info("Processing book with id={}", bookId);
```

Avoid logging:

- Passwords
- JWT tokens
- Database credentials
- Sensitive user information
- Complete uploaded documents

---

# 28. Testing

The project includes:

```text
spring-boot-starter-test
```

Testing should cover:

### Unit Tests

- Service logic
- Validation
- PDF processing logic
- Utility behavior

### Repository Tests

- Database queries
- JPA mappings

### Integration Tests

- REST APIs
- Security
- Database interaction
- File upload behavior

Recommended testing stack:

```text
JUnit 5
Mockito
Spring Boot Test
```

---

# 29. Build and Run

## Prerequisites

Install:

```text
Java 25
PostgreSQL 18
Maven
```

Verify Java:

```bash
java -version
```

Verify PostgreSQL:

```sql
SELECT version();
```

---

## Run the Application

From the project root:

```bash
mvn spring-boot:run
```

If Maven Wrapper is included:

### Windows

```cmd
mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

---

# 30. Build the Application

```bash
mvn clean package
```

Or using Maven Wrapper:

```cmd
mvnw.cmd clean package
```

The generated JAR will be available under:

```text
target/
```

---

# 31. Configuration

Application configuration should be externalized.

Example:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Environment-specific configuration should not be committed with secrets.

Recommended environments:

```text
application.properties
application-dev.properties
application-test.properties
application-prod.properties
```

---

# 32. Recommended Production Improvements

Before deploying this application to production, consider adding:

- Flyway database migrations
- API documentation using OpenAPI
- Actuator health endpoints
- Centralized exception responses
- Structured logging
- Request correlation IDs
- Database connection pool tuning
- Pagination
- Rate limiting
- Object storage for PDFs
- Background processing for expensive PDF operations
- Docker containerization
- CI/CD pipeline
- Automated integration tests
- Monitoring and metrics

---

# 33. Development Principles

The project follows these principles:

### Separation of Concerns

Controllers should handle HTTP concerns.

Services should contain business logic.

Repositories should handle persistence.

### Dependency Injection

Prefer constructor injection:

```java
public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
}
```

### DTOs

Avoid exposing JPA entities directly through public APIs.

### Validation

Validate all externally supplied input.

### Secure by Default

Every endpoint should have an intentional authentication and authorization policy.

### Configuration

Avoid hardcoded environment-specific values.

---

# 34. Current Project Status

Current capabilities include:

```text
[✓] Spring Boot application
[✓] PostgreSQL integration
[✓] JPA / Hibernate
[✓] Book management
[✓] Author management
[✓] Category management
[✓] PDF upload
[✓] PDF processing
[✓] First-page extraction
[✓] Thumbnail generation
[✓] JWT authentication
[✓] Spring Security
[✓] Validation
[✓] Email support
[✓] ONNX Runtime integration
```

---

# 35. Future Improvements

Potential future enhancements include:

```text
[ ] Database migration management
[ ] Object storage integration
[ ] Advanced book search
[ ] Full-text search
[ ] Background PDF processing
[ ] Caching
[ ] Redis integration
[ ] Docker deployment
[ ] CI/CD
[ ] Observability
[ ] Distributed processing
```

---

# 36. License

Add the appropriate project license here.

Example:

```text
This project is proprietary software.
```

---

# 37. Author

**Dileep**

Backend Developer  
Java | Spring Boot | PostgreSQL | Microservices