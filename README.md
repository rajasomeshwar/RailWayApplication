# Railway Management System

A Spring Boot-based Railway Management System (similar to IRCTC) that allows users to:

- **Register & Login:** Users can register and then log in using JWT-based authentication.
- **Search Trains:** Users can search for available trains between two stations.
- **Book Seats:** Users can book seats on a train if seats are available. The booking mechanism is designed to handle concurrency (i.e., only one user can book a particular seat when multiple users attempt to book simultaneously).
- **View Booking Details:** Users can retrieve details of their bookings.
- **Admin Functionality:** Admin users can add new trains, update train details, and list all trains. Admin endpoints are additionally secured using an API key.

> **Note:** This project uses MySQL as the database. Ensure your database is set up correctly before running the application.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [API Endpoints](#api-endpoints)
  - [User Endpoints](#user-endpoints)
  - [Admin Endpoints](#admin-endpoints)
- [Exception Handling](#exception-handling)
- [Local Setup & Running the Application](#local-setup--running-the-application)
- [Database Configuration](#database-configuration)
- [Sample API Usage](#sample-api-usage)
- [Additional Notes](#additional-notes)

---

## Features

- **User Registration & Login:** Secure endpoints using JWT authentication.
- **Train Search:** Find trains based on source and destination stations.
- **Real-Time Seat Availability:** Display available seats for each train.
- **Seat Booking:** Book seats with concurrency handling using transactions.
- **Booking Details:** Retrieve specific booking details.
- **Admin Operations:**
  - Add a new train
  - Update seat availability
  - List all trains
- **Security:**
  - Admin endpoints are protected by an API key.
  - User endpoints require a JWT token in the `Authorization` header.

---

## Tech Stack

- **Backend:** Java Spring Boot
- **Database:** MySQL
- **Security:** Spring Security, JWT Authentication
- **Build Tool:** Maven

---

## API Endpoints

### User Endpoints

1. **Register a User**

   - **URL:** `POST /api/auth/register`
   - **Request Body:**
     ```json
     {
       "username": "john_doe",
       "password": "password123",
       "role": "USER"
     }
     ```
   - **Response:**  
     Success message:  
     ```
     "User registered successfully"
     ```
   - **Error:**  
     An error message if the user already exists or validation fails.

2. **Login User**

   - **URL:** `POST /api/auth/login`
   - **Request Body:**
     ```json
     {
       "username": "john_doe",
       "password": "password123"
     }
     ```
   - **Response:**
     ```json
     {
       "user": {
         "username": "john_doe",
         "role": "USER"
       },
       "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
     }
     ```
   - **Usage:**  
     The returned JWT must be included in the `Authorization` header (e.g., `Authorization: Bearer <JWT_TOKEN>`) for subsequent user API calls.

3. **Get Seat Availability (Search Trains)**

   - **URL:** `GET /api/user/trains?source=Mumbai&destination=Delhi`
   - **Query Parameters:**  
     - `source`: Starting station  
     - `destination`: Destination station
   - **Response:**
     ```json
     [
       {
         "id": 1,
         "trainNumber": 12345,
         "trainName": "Express Line",
         "source": "Mumbai",
         "destination": "Delhi",
         "totalSeats": 100,
         "availableSeats": 50,
         "arrivalTime": "2025-05-10T20:00:00"
       }

     ]
     ```

4. **Book a Seat**

   - **URL:** `POST /api/userx/bookings`
   - **Headers:**
     ```
     Authorization: Bearer <JWT_TOKEN>
     ```
   - **Request Body:**
     ```json
     {
       "trainId": 1,
       "requiredSeats": 1
     }
     ```
   - **Response:**
     ```json
     {
       "bookingId": 5,
       "user": {
         "username": "john_doe"
       },
       "train": {
         "id": 1,
         "trainNumber": 12345,
         "trainName": "Express Line",
         "source": "Mumbai",
         "destination": "Delhi"
       },
       "status": "Confirmed"
     }
     ```
   - **Error Handling:**  
     - If seats are unavailable, returns `"No seats available"`.
     - If JWT is invalid or user not found, throws an exception (e.g., `TokenExpiredLoginAgainException`).

5. **Get Specific Booking Details**

   - **URL:** `GET /api/userx/bookings/{bookingId}`
   - **Headers:**
     ```
     Authorization: Bearer <JWT_TOKEN>
     ```
   - **Response:**
     ```json
     {
       "bookingId": 5,
       "user": {
         "username": "john_doe"
       },
       "train": {
         "id": 1,
         "trainNumber": 12345,
         "trainName": "Express Line",
         "source": "Mumbai",
         "destination": "Delhi"
       },
       "status": "Confirmed"
     }
     ```

6. **Get All Bookings for a User**

   - **URL:** `GET /api/userx/bookingsAll`
   - **Headers:**
     ```
     Authorization: Bearer <JWT_TOKEN>
     ```
   - **Response:**
     ```json
     [
       {
         "bookingId": 5,
         "user": {
           "username": "john_doe"
         },
         "train": { /* train details */ },
         "status": "Confirmed"
       }
     ]
     ```

### Admin Endpoints

> **Important:** All admin endpoints require an additional header:
>
> ```
> API-Key: your-admin-api-key
> ```

1. **Add a New Train**

   - **URL:** `POST /api/admin/trains`
   - **Headers:**
     ```
     Authorization: Bearer <ADMIN_JWT_TOKEN>
     API-Key: your-admin-api-key
     ```
   - **Request Body:**
     ```json
     {
       "trainNumber": 12345,
       "trainName": "Express Line",
       "source": "Mumbai",
       "destination": "Delhi",
       "totalSeats": 100,
       "arrivalTime": "2025-05-10T20:00:00"
     }
     ```
   - **Response:**
     ```json
     {
       "trainNumber": 12345,
       "trainName": "Express Line",
       "source": "Mumbai",
       "destination": "Delhi",
       "totalSeats": 100,
       "availableSeats": 100,
       "arrivalTime": "2025-05-10T20:00:00"
     }
     ```

2. **Update Train Seat Availability**

   - **URL:** `POST /api/admin/trainUpdate`
   - **Headers:**
     ```
     Authorization: Bearer <ADMIN_JWT_TOKEN>
     API-Key: your-admin-api-key
     ```
   - **Request Body:**
     ```json
     {
       "trainNumber": 12345,
       "newAvailable": 75
     }
     ```
   - **Response:**  
     Returns the updated train object.

3. **List All Trains**

   - **URL:** `GET /api/admin/trains`
   - **Headers:**
     ```
     Authorization: Bearer <ADMIN_JWT_TOKEN>
     API-Key: your-admin-api-key
     ```
   - **Response:**
     ```json
     [
       {
         "trainNumber": 12345,
         "trainName": "Express Line",
         "source": "Mumbai",
         "destination": "Delhi",
         "totalSeats": 100,
         "availableSeats": 75,
         "arrivalTime": "2025-05-10T20:00:00"
       }
       // ... more train objects
     ]
     ```

---

## Exception Handling

- **User Not Found / Invalid JWT:**  
  - Throws exceptions like `TokenExpiredLoginAgainException` or a `RuntimeException` with a message such as `"User not found"` or `"Invalid JWT Token! Please login again."`
- **No Seats Available:**  
  - Throws a `RuntimeException` with the message `"No seats available"`.
- **Validation Errors:**  
  - Uses Spring validation annotations (e.g., `@NotBlank`, `@Positive`) to ensure incoming requests are valid. Validation errors return detailed messages.

---

## Local Setup & Running the Application

### Prerequisites

- **Java 11 or higher**
- **Maven 3.6+**
- **MySQL 8 or higher**

### Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/railway-management-system.git
   cd railway-management-system
