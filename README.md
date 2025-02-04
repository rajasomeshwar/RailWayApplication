Railway Management System
This is a Spring Boot-based Railway Management System (similar to IRCTC) where:

Users can register, log in, search for trains between two stations (with seat availability), book seats, and view booking details.
Admins can add new trains and update train seat information.
The application handles race conditions so that if multiple users try to book seats simultaneously, only one succeeds when seats are limited.
Security: JWT authentication is used for user endpoints. Admin endpoints are additionally secured via an API key.
Table of Contents
Features
Tech Stack
Endpoints
User Endpoints
Admin Endpoints
Exception Handling
Local Setup & Running the Application
Database Configuration
API Examples
Additional Notes
Features
User Registration & Login (JWT based authentication)
Train Search by providing source and destination stations
Real-Time Seat Availability display
Seat Booking with race condition handling (using transactions and locking)
View Booking Details for individual bookings
Admin Functionality:
Add a new train
Update train seat availability
Security:
All admin endpoints require an API key (set in request headers)
User endpoints require the JWT in the Authorization header
Tech Stack
Backend: Spring Boot (Java)
Database: MySQL
Security: Spring Security with JWT Authentication
Build Tool: Maven
Endpoints
User Endpoints
Register a User

URL: POST /api/auth/register
Input (JSON):
json
Copy
Edit
{
  "username": "john_doe",
  "password": "password123",
  "role": "USER"
}
Output:
Success: "User registered successfully"
Error: Returns appropriate error message (e.g., if user already exists).
Login User

URL: POST /api/auth/login
Input (JSON):
json
Copy
Edit
{
  "username": "john_doe",
  "password": "password123"
}
Output (JSON):
json
Copy
Edit
{
  "user": { /* user details */ },
  "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
Notes: The returned JWT token should be used in subsequent calls to protected endpoints in the header:
Authorization: Bearer <JWT_TOKEN>
Get Seat Availability (Search Trains)

URL: GET /api/user/trains?source=Mumbai&destination=Delhi
Input: Query parameters source and destination
Output (JSON):
json
Copy
Edit
[
  {
    "id": 1,
    "trainNumber": 12345,
    "trainName": "Express Line",
    "source": "Mumbai",
    "destination": "Delhi",
    "totalSeats": 100,
    "availableSeats": 50,
    "departureTime": "2025-05-10T08:00:00",
    "arrivalTime": "2025-05-10T20:00:00"
  },
 
]
Book a Seat

URL: POST /api/userx/bookings
Input (JSON):
json
Copy
Edit
{
  "trainId": 1,
  "requiredSeats": 1
}
Headers: Authorization: Bearer <JWT_TOKEN>
Output (JSON):
json
Copy
Edit
{
  "bookingId": 5,
  "user": { "username": "john_doe" },
  "train": {
    "id": 1,
    "trainNumber": 12345,
    "trainName": "Express Line",
    "source": "Mumbai",
    "destination": "Delhi"
  },
  "status": "Confirmed"
}
Error Handling:
If no seats are available, the API returns a message such as "No seats available".
If the JWT is invalid or the user is not found, an exception like TokenExpiredLoginAgainException will be thrown.
Get Specific Booking Details

URL: GET /api/userx/bookings/{bookingId}
Input: Path variable bookingId
Headers: Authorization: Bearer <JWT_TOKEN>
Output (JSON):
json
Copy
Edit
{
  "bookingId": 5,
  "user": { "username": "john_doe" },
  "train": {
    "id": 1,
    "trainNumber": 12345,
    "trainName": "Express Line",
    "source": "Mumbai",
    "destination": "Delhi"
  },
  "status": "Confirmed"
}
Get All Bookings for a User

URL: GET /api/userx/bookingsAll
Headers: Authorization: Bearer <JWT_TOKEN>
Output (JSON):
json
Copy
Edit
[
  {
    "bookingId": 5,
    "user": { "username": "john_doe" },
    "train": { /* train details */ },
    "status": "Confirmed"
  },

]
Admin Endpoints
Note: All admin endpoints require the header:
API-Key: your-admin-api-key

Add a New Train

URL: POST /api/admin/trains
Headers: Authorization: Bearer <ADMIN_JWT_TOKEN>, API-Key: your-admin-api-key
Input (JSON):
json
Copy
Edit
{
  "trainNumber": 12345,
  "trainName": "Express Line",
  "source": "Mumbai",
  "destination": "Delhi",
  "totalSeats": 100,
  "departureTime": "2025-05-10T08:00:00",
  "arrivalTime": "2025-05-10T20:00:00"
}
Output:
Success: Returns the created train details with a message such as "Train added successfully".
Error: Returns an error message if the train data is invalid.
Update Train Seat Availability

URL: POST /api/admin/trainUpdate
Headers: Authorization: Bearer <ADMIN_JWT_TOKEN>, API-Key: your-admin-api-key
Input (JSON):
json
Copy
Edit
{
  "trainNumber": 12345,
  "newAvailable": 75
}
Output:
Success: Returns the updated train object.
Error: Returns an error message if the update fails.
List All Trains

URL: GET /api/admin/trains
Headers: Authorization: Bearer <ADMIN_JWT_TOKEN>, API-Key: your-admin-api-key
Output (JSON):
json
Copy
Edit
[
  {
    "trainNumber": 12345,
    "trainName": "Express Line",
    "source": "Mumbai",
    "destination": "Delhi",
    "totalSeats": 100,
    "availableSeats": 75,
    "departureTime": "2025-05-10T08:00:00",
    "arrivalTime": "2025-05-10T20:00:00"
  }
]
Exception Handling
User Not Found / Invalid JWT:
The system throws TokenExpiredLoginAgainException or a RuntimeException with a message like "User not found" or "Invalid JWT Token! Please login again.".

No Seats Available:
When a user attempts to book a seat and none are available, a RuntimeException is thrown with the message "No seats available".

Validation Errors:
The application uses Spring's validation annotations (e.g., @NotBlank, @Positive) to ensure that incoming requests are valid.
If validation fails, a detailed error message is returned.

Local Setup & Running the Application
Prerequisites
Java 11 or above
Maven 3.6+
MySQL 8 or above
Steps
Clone the Repository

bash
Copy
Edit
git clone https://github.com/yourusername/railway-management-system.git
cd railway-management-system
Configure the Database

Update the src/main/resources/application.properties file with your database settings. For example:

properties
Copy
Edit
spring.application.name=RailWayApplication
server.port=8085

spring.datasource.url=jdbc:mysql://localhost:3306/RailWayDB1?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_mysql_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.defer-datasource-initialization=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
management.endpoints.web.exposure.include=*
logging.level.org.springframework.web=DEBUG
Note: Replace your_mysql_password with your actual MySQL password.

Build the Project

bash
Copy
Edit
mvn clean install
Run the Application

bash
Copy
Edit
mvn spring-boot:run
The application will start on port 8085.

Run Tests (if available)

bash
Copy
Edit
mvn test
Database Configuration
Database URL: jdbc:mysql://localhost:3306/RailWayDB1?createDatabaseIfNotExist=true
Username: root
Password: your_mysql_password
The application will automatically create/update tables based on the JPA entities.

API Examples
Example 1: Register and Login
Register

Endpoint: POST /api/auth/register
Request Body:
json
Copy
Edit
{
  "username": "john_doe",
  "password": "password123",
  "role": "USER"
}
Response:
arduino
Copy
Edit
"User registered successfully"
Login

Endpoint: POST /api/auth/login
Request Body:
json
Copy
Edit
{
  "username": "john_doe",
  "password": "password123"
}
Response:
json
Copy
Edit
{
  "user": { "username": "john_doe", "role": "USER" },
  "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
Example 2: Admin Adds a Train
Endpoint: POST /api/admin/trains
Headers:
vbnet
Copy
Edit
Authorization: Bearer <ADMIN_JWT_TOKEN>
API-Key: your-admin-api-key
Request Body:
json
Copy
Edit
{
  "trainNumber": 12345,
  "trainName": "Express Line",
  "source": "Mumbai",
  "destination": "Delhi",
  "totalSeats": 100,
  "departureTime": "2025-05-10T08:00:00",
  "arrivalTime": "2025-05-10T20:00:00"
}
Response:
json
Copy
Edit
{
  "trainNumber": 12345,
  "trainName": "Express Line",
  "source": "Mumbai",
  "destination": "Delhi",
  "totalSeats": 100,
  "availableSeats": 100,
  "departureTime": "2025-05-10T08:00:00",
  "arrivalTime": "2025-05-10T20:00:00"
}
Example 3: User Searches for Trains
Endpoint: GET /api/user/trains?source=Mumbai&destination=Delhi
Headers:
makefile
Copy
Edit
Authorization: Bearer <USER_JWT_TOKEN>
Response:
json
Copy
Edit
[
  {
    "trainNumber": 12345,
    "trainName": "Express Line",
    "source": "Mumbai",
    "destination": "Delhi",
    "totalSeats": 100,
    "availableSeats": 90,
    "departureTime": "2025-05-10T08:00:00",
    "arrivalTime": "2025-05-10T20:00:00"
  }
]
Example 4: User Books a Seat
Endpoint: POST /api/userx/bookings
Headers:
makefile
Copy
Edit
Authorization: Bearer <USER_JWT_TOKEN>
Request Body:
json
Copy
Edit
{
  "trainId": 1,
  "requiredSeats": 1
}
Response:
json
Copy
Edit
{
  "bookingId": 5,
  "user": { "username": "john_doe" },
  "train": {
    "id": 1,
    "trainNumber": 12345,
    "trainName": "Express Line",
    "source": "Mumbai",
    "destination": "Delhi"
  },
  "status": "Confirmed"
}
Example 5: Get Booking Details
Endpoint: GET /api/userx/bookings/5
Headers:
makefile
Copy
Edit
Authorization: Bearer <USER_JWT_TOKEN>
Response:
json
Copy
Edit
{
  "bookingId": 5,
  "user": { "username": "john_doe" },
  "train": {
    "id": 1,
    "trainNumber": 12345,
    "trainName": "Express Line",
    "source": "Mumbai",
    "destination": "Delhi"
  },
  "status": "Confirmed"
}
Additional Notes
API Key Protection:
All endpoints under /api/admin require a header API-Key with the correct value. Ensure that this key is kept secret and only shared with admins.

Concurrency:
The seat booking functionality is protected by transactions to ensure that simultaneous booking requests do not result in overbooking.

Validation & Exception Handling:
The code uses Spring validation annotations (e.g., @NotBlank, @Positive) to validate incoming requests. Custom exceptions are thrown when required (e.g., user not found, no seats available).

Testing:
You can add integration tests with JUnit and Spring Boot Test to validate the endpoints.

Port:
The application runs on port 8085 as configured in application.properties.

