# Railway Management System

A Spring Boot-based Railway Management System (similar to IRCTC) that allows users to:

- **Register & Login:** Users can register and then log in using JWT-based authentication.
- **Search Trains:** Users can search for available trains between two stations.
- **Book Seats:** Users can book seats on a train if seats are available. The booking mechanism is designed to handle concurrency (i.e., only one user can book a particular seat when multiple users attempt to book simultaneously).
- **View Booking Details:** Users can retrieve details of their bookings.
- **Admin Functionality:** Admin users (default username: **admin** , password : **password** ) can add new trains, update train details, and list all trains. Admin endpoints are additionally secured using an API key.

> **Note:** This project uses MySQL as the database. Ensure your database is set up correctly before running the application. The port Number is : **8085** default 

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
       "username": "johndoeu",
       "password": "securePassword123",
       
     }
     ```
   - **Response:**  
     ```
     "Account Created !"
     ```
   - **Notes:**  
     Registration does not require a JWT token.

2. **Login User**

   - **URL:** `POST /api/auth/login`
   - **Request Body:**
     ```json
     {
       "username": "johndoe",
       "password": "securePassword123"
     }
     ```
   - **Response:**
     ```json
     {
       "user": {
         "userId": 2,
         "username": "johndoe",
         "enabled": true,
         "accountNonLocked": true,
         "credentialsNonExpired": true,
         "accountNonExpired": true
       },
       "jwt": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiam9obmRvZSIsImV4cCI6MTczODY5NDQyMywiaWF0IjoxNzM4Njg5MDIzLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXX0.hpAmjKuAdpwVYuylLG7eryhwVTMAAjL4UrrhcT6mP5ITInYHkdoq1IP79toyChYydIsoVBpR3dslpsi3-h_AppA8b9z_srubXMdbeJAAyKoIp5hF2_FSwSZ2TNRTv2rv24i_a-yKTxk2HyPmGVCCzdwdAs65Nz1teCoHbX2UBeQnZ-eFt1ZGVm2fy9OmI4sEj0Q4PR3Ss6ysqzgES2S-ehypzGoiJWxCCUgcCkkEEpdEvOosp6r9-wJzD1XCqFlEuFqhy_-CIXJemXZu98LAUGbDkMzyoWuhdShoP2S83nhc5_TUQX9GWarXCeA3N1KOAKFGUp3RAH93YDFXeY0x-g"
     }
     ```
   - **Usage:**  
     The returned JWT must be included in the `Authorization` header (e.g., `Authorization: Bearer <JWT_TOKEN>`) for all subsequent user API calls.

3. **Get Seat Availability (Search Trains)**

   - **URL:** `GET /api/user/trains?source=Mumbai&destination=Delhi`
   - **Query Parameters:**
     - `source`: Starting station
     - `destination`: Destination station
   - **Headers:**
     ```
     Authorization: Bearer <USER_JWT_TOKEN>
     ```
   - **Response:**
     ```json
     [
       {
         "id": 1,
         "trainNumber": 12345,
         "trainName": "Express Train",
         "source": "Mumbai",
         "destination": "Delhi",
         "totalSeats": 500,
         "availableSeats": 482,
         "arrivalTime": "2024-02-04T18:00:00",
         "version": 9
       },
       {
         "id": 2,
         "trainNumber": 12349,
         "trainName": "Express Train",
         "source": "Mumbai",
         "destination": "Delhi",
         "totalSeats": 500,
         "availableSeats": 498,
         "arrivalTime": "2024-02-04T18:00:00",
         "version": 1
       }
     ]
     ```

4. **Book a Seat**

   - **URL:** `POST /api/userx/bookings`
   - **Headers:**
     ```
     Authorization: Bearer <USER_JWT_TOKEN>
     ```
   - **Request Body:**
     ```json
     {
       "trainId": 1,
       "requiredSeats": 2
     }
     ```
   - **Response:**  
     (Sample response)
     ```json
     {
       "id": 10,
       "train": {
         "id": 1,
         "trainNumber": 12345,
         "trainName": "Express Train",
         "source": "Mumbai",
         "destination": "Delhi",
         "totalSeats": 500,
         "availableSeats": 482,
         "arrivalTime": "2024-02-04T18:00:00",
         "version": 9
       },
       "user": {
         "userId": 2,
         "username": "johndoe",
         "enabled": true,
         "accountNonLocked": true,
         "credentialsNonExpired": true,
         "accountNonExpired": true
       },
       "bookingTime": "2025-02-04T22:32:58.1179446"
     }
     ```
   - **Error Handling:**  
     - If seats are unavailable, returns `"No seats available"`.
     - If JWT is invalid or the user is not found, an exception such as `TokenExpiredLoginAgainException` is thrown.

5. **Get Specific Booking Details**

   - **URL:** `GET /api/userx/bookings/{bookingId}`
   - **Headers:**
     ```
     Authorization: Bearer <USER_JWT_TOKEN>
     ```
   - **Response:**  
     (Sample response)
     ```json
     {
       "id": 10,
       "train": {
         "id": 1,
         "trainNumber": 12345,
         "trainName": "Express Train",
         "source": "Mumbai",
         "destination": "Delhi",
         "totalSeats": 500,
         "availableSeats": 482,
         "arrivalTime": "2024-02-04T18:00:00",
         "version": 9
       },
       "user": {
         "userId": 2,
         "username": "johndoe",
         "enabled": true,
         "accountNonLocked": true,
         "credentialsNonExpired": true,
         "accountNonExpired": true
       },
       "bookingTime": "2025-02-04T22:32:58.1179446"
     }
     ```

6. **Get All Bookings for a User**

   - **URL:** `GET /api/userx/bookingsAll`
   - **Headers:**
     ```
     Authorization: Bearer <USER_JWT_TOKEN>
     ```
   - **Response:**  
     (Sample response)
     ```json
     [
       {
         "id": 1,
         "train": {
           "id": 1,
           "trainNumber": 12345,
           "trainName": "Express Train",
           "source": "Mumbai",
           "destination": "Delhi",
           "totalSeats": 500,
           "availableSeats": 482,
           "arrivalTime": "2024-02-04T18:00:00",
           "version": 9
         },
         "user": {
           "userId": 2,
           "username": "johndoe",
           "enabled": true,
           "accountNonLocked": true,
           "credentialsNonExpired": true,
           "accountNonExpired": true
         },
         "bookingTime": "2025-02-04T22:32:58.117944"
       }
       // ... additional bookings
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
       "trainNumber": "12345",
       "trainName": "Express Train",
       "source": "Mumbai",
       "destination": "Delhi",
       "totalSeats": 500,
       "arrivalTime": "2024-02-04T18:00:00"
     }
     ```
   - **Response:**  
     (Sample response)
     ```json
     {
       "trainId": 1,
       "trainDetails": {
         "id": 1,
         "trainNumber": "12345",
         "trainName": "Express Train",
         "source": "Mumbai",
         "destination": "Delhi",
         "totalSeats": 500,
         "availableSeats": 500,
         "arrivalTime": "2024-02-04T18:00:00",
         "version": 0
       },
       "trainNumber": "12345",
       "message": "Train added successfully"
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
     (Sample response)
     ```json
     [
       {
         "trainNumber": 12345,
         "trainName": "Express Train",
         "source": "Mumbai",
         "destination": "Delhi",
         "totalSeats": 500,
         "availableSeats": 75,
         "arrivalTime": "2024-02-04T18:00:00"
       }
     ]
     ```

---

## Exception Handling

- **User Not Found / Invalid JWT:**  
  Throws exceptions like `TokenExpiredLoginAgainException` or a `RuntimeException` with a message such as `"User not found"` or `"Invalid JWT Token! Please login again."`

- **No Seats Available:**  
  Throws a `RuntimeException` with the message `"No seats available"`.

- **Validation Errors:**  
  Uses Spring validation annotations (e.g., `@NotBlank`, `@Positive`) to ensure incoming requests are valid. Validation errors return detailed messages.

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
