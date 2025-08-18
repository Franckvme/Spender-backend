# Spender-backend
Architecture Pattern: Clean Architecture / Hexagonal Architecture with well-defined layers and clear separation of concerns.
Key Components:

Controllers: REST endpoints with proper validation and authorization
Domain Services: Business logic orchestration layer
Use Cases: Specific business operations (@UseCaseCommand, @UseCaseQuery)
Data Stores: Persistence abstraction layer
Repositories: JPA data access layer

Security Implementation:

JWT-based authentication with custom filter
Role-based authorization using @PreAuthorize
Secure token generation and claims extraction

Strengths:

Strong separation of concerns
Custom annotations for architectural clarity
Proper transaction management
Robust error handling with custom exceptions
Clean entity design with builder pattern

Areas for Improvement:

Role/Permission system partially implemented (entities exist but not fully integrated in JWT)
Hardcoded authorities in token generation
Some architectural inconsistencies in controller layer

Overall Assessment: Solid, maintainable architecture following modern Spring Boot best practices with room for role-based security enhancement.
