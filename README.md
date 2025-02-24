# Task Manager API

## 📌 Overview
Task Manager API is a RESTful service that allows users to manage their tasks efficiently. It provides authentication and user management, along with CRUD operations for tasks.

## 🚀 Technologies Used
- **Java 23**
- **Spring Boot 3.4.3**
- **PostgreSQL**
- **Spring Security** (JWT Authentication)
- **Hibernate** (JPA for ORM)
- **Lombok** (For reducing boilerplate code)
- **MapStruct** (For DTO Mapping)
- **JUnit & Mockito** (For testing)

---

## ⚙️ Project Setup
### 1 Clone the Repository
```sh
  git clone https://github.com/emmanuel-lehmann/taskmanager.git
  cd taskmanager
```

### 2 Set Up PostgreSQL Database
Ensure you have PostgreSQL installed and running, then create the database:
```sql
CREATE DATABASE task_manager;
```

### 3 Configure Database Credentials
Update the `src/main/resources/application.properties` file with your database credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/task_manager
spring.datasource.username=your_user
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### 4 Run the Application
Run the application using Maven:
```sh
  mvn clean install -U
  mvn spring-boot:run
```

---

## 🔑 Authentication & Security
- Uses **JWT (JSON Web Token)** for authentication
- Implements **Spring Security** for user management
- **CSRF Disabled** (Since it's a REST API)
- **Role-Based Access Control (RBAC)** planned for future updates

### **Authentication Endpoints**
| Method | Endpoint | Description |
|--------|-------------|-------------|
| `POST`  | `/api/auth/register` | Register a new user |
| `POST`  | `/api/auth/login` | Authenticate a user & get JWT token |

#### **Register a User** (`POST /api/auth/register`)
**Request Body:**
```json
{
  "username": "john_doe",
  "password": "securepassword"
}
```
**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsIn...",
  "user": "john_doe"
}
```

#### **Login a User** (`POST /api/auth/login`)
**Request Body:**
```json
{
  "username": "john_doe",
  "password": "securepassword"
}
```
**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsIn...",
  "user": "john_doe"
}
```

Use this token in the `Authorization` header for protected routes:
```
Authorization: Bearer <your-jwt-token>
```

---

## 🛠️ API Endpoints
### 📌 **Task Endpoints**
| Method | Endpoint | Description | Authentication Required |
|--------|-------------|-------------|--------------------|
| `GET`  | `/api/tasks` | Retrieve all tasks | ✅ |
| `GET`  | `/api/tasks/{id}` | Retrieve a task by ID | ✅ |
| `POST` | `/api/tasks` | Create a new task | ✅ |
| `PUT`  | `/api/tasks/{id}` | Update an existing task | ✅ |
| `DELETE` | `/api/tasks/{id}` | Delete a task | ✅ |
| `PUT`  | `/api/tasks/{taskId}/assign/{userId}` | Assign task to a user | ✅ |

### 📌 **User Endpoints**
| Method | Endpoint | Description | Authentication Required |
|--------|-------------|-------------|--------------------|
| `GET`  | `/api/users` | Get all users | ✅ |

---

## 📌 Request/Response Examples
#### **Create a Task** (`POST /api/tasks`)
**Request Body:**
```json
{
  "title": "Finish project",
  "status": "INCOMPLETE"
}
```
**Response:**
```json
{
  "id": 1,
  "title": "Finish project",
  "status": "INCOMPLETE",
  "assignedTo": null
}
```

#### **Assign a Task to a User** (`PUT /api/tasks/{taskId}/assign/{userId}`)
**Response:**
```json
{
  "id": 1,
  "title": "Finish project",
  "status": "INCOMPLETE",
  "assignedTo": {
    "id": 2,
    "username": "johndoe"
  }
}
```

---

## 🔄 Running Tests
To execute unit tests, run:
```sh
  mvn test
```
### **Test Coverage Includes:**
✅ **Unit Tests** with JUnit & Mockito  
✅ **Integration Tests** for Controller & Service layers  
✅ **Authentication Tests** for security flows

---

## 🐟 License
This project is open-source and available under the MIT License.

---


