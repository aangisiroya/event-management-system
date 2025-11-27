# event-management-system
(Full Stack)

A full-stack Event Management System built using:
React + Vite (Frontend)
Spring Boot (Backend)
MongoDB Atlas (Database)
JWT Authentication (Login/Role Based Access)
Supports Admin and User roles with separate permissions.

Features
User
Register and Login using JWT
View all events
Register for events
View their registrations

Admin
Create events
Delete events
View event-wise registrations

Tech Stack
Layer	Technology
Frontend-React, Vite, Axios, DayJS
Backend	-Spring Boot, Java 17
Database-MongoDB Atlas
Authentication-JWT
Project Structure
event-management-system/
 ├── backend/
 │    ├── src/main/java/com/example/event/
 │    ├── src/main/resources/application.properties
 │    ├── pom.xml
 ├── frontend/
 │    ├── src/App.jsx
 │    ├── src/main.jsx
 │    ├── package.json
 │    ├── vite.config.js
 └── README.md

How to Run the Project
1. Clone the repository
git clone https://github.com/aangisiroya/event-management-system.git
cd event-management-system

Backend Setup (Spring Boot)
2. Open backend folder
cd backend

3. Configure MongoDB

Edit the file:
src/main/resources/application.properties

Example configuration:

spring.data.mongodb.uri=mongodb+srv://admin:1234@cluster0.mongodb.net/eventdb
jwt.secret=MyVeryStrongJwtSecretKey123456789!!!
server.port=8080


Ensure MongoDB Network Access allows:
0.0.0.0/0

4. Run Backend
mvn spring-boot:run


Backend URL:
http://localhost:8080

Frontend Setup (React + Vite)
5. Open frontend folder
cd ../frontend

6. Install dependencies
npm install

7. Start frontend
npm run dev


Frontend URL:
http://localhost:5173

Login Credentials

To create Admin, choose ADMIN in role dropdown while registering.
To create User, choose USER in role dropdown.

API Endpoints
Public
Method	Endpoint	Description
GET	/events	Get all events
User Endpoints
Method	Endpoint	Description
POST	/events/{id}/register	Register for an event
GET	/events/my	View user’s registrations
Admin Endpoints
Method	Endpoint	Description
POST	/admin/events	Create event
DELETE	/admin/events/{id}	Delete event
GET	/admin/events/{id}/registrations	View registrations for event

DELIVERABLE-2

Below are the backend REST APIs implemented in the Event Management System.
They cover authentication, role-based access, and event CRUD operations.

----------------------------------------------------
1. Authentication APIs
----------------------------------------------------

POST /auth/register
Description: Registers a new user or admin.
Request Body:
{
  "username": "testuser",
  "password": "1234",
  "role": "USER" / "ADMIN"
}
Response:
{ "msg": "registered" }

POST /auth/login
Description: Authenticates a user and returns a JWT token.
Request Body:
{
  "username": "testuser",
  "password": "1234"
}
Response:
{
  "token": "<jwt_token>",
  "role": "USER" / "ADMIN"
}

----------------------------------------------------
2. Event APIs (Public)
----------------------------------------------------

GET /events
Description: Fetch all events.
Response: List of event objects.

GET /events?category=Tech
Description: Fetch events by category.

GET /events/my
Description: Gets all events registered by the logged-in user.
Headers:
Authorization: Bearer <token>

----------------------------------------------------
3. Event CRUD (Admin Only)
----------------------------------------------------

POST /admin/events
Description: Create a new event. Only admins can access this.
Headers:
Authorization: Bearer <token>
Request Body:
{
  "title": "AI Conference",
  "description": "Tech talk",
  "location": "Pune",
  "category": "Tech",
  "dateTime": "2025-12-10T10:00",
  "seats": 50
}
Response:
{ "msg": "created" }

DELETE /admin/events/{id}
Description: Deletes an event. Admin only.
Headers:
Authorization: Bearer <token>
Response:
{ "msg": "deleted" }

GET /admin/events/{id}/registrations
Description: Lists all registered users for a specific event.
Headers:
Authorization: Bearer <token>

----------------------------------------------------
4. Event Registration
----------------------------------------------------

POST /events/{id}/register
Description: Normal user registers for event.
Headers:
Authorization: Bearer <token>
Response:
{ "msg": "registered" }

DELIVERABLE 3
## Deliverable 5 – Basic Frontend UI Connected to APIs

The frontend of this project is built using React (Vite) and is fully integrated with the backend APIs for authentication and event management. The UI communicates with the backend using Axios and uses the JWT token for all authorized requests.

### 1. Authentication UI (Connected to /auth APIs)
The frontend sends requests to:
- POST /auth/register  
- POST /auth/login  

After successful login:
- The JWT token and role returned from the backend are stored in localStorage.
- The user is redirected to the dashboard.

### 2. Dashboard UI Integrated With Backend APIs
Once the user logs in, the dashboard connects to the following APIs:

#### Public API:
- GET /events  
  Used to load and display all events on the dashboard.

#### User APIs:
- POST /events/{id}/register  
  Allows user to register for an event.

#### Admin APIs:
- POST /admin/events  
  Used by admin to create a new event.
- DELETE /admin/events/{id}  
  Allows admin to delete an event.
- GET /admin/events/{id}/registrations  
  Shows admin all registrations for a specific event.

### 3. Form Inputs Connected to Backend
The event creation form includes:
- Title  
- Description  
- Category  
- Location  
- Date and Time (sent to backend as ISO date string)  
- Seats  

When the admin clicks "Create", the form sends a POST request to:
- POST /admin/events

All fields are sent in JSON as required by the backend API.

### 4. Event List Rendering (Connected Data)
The frontend calls GET /events on load, and displays all events returned by the backend. Each event item shows:
- Title  
- Category  
- Date  
- Time  
- Location  
- Number of seats  
- Created by  

Buttons displayed:
- Register (USER)
- Delete (ADMIN)

### 5. Token-Based Authorization (Frontend to Backend)
Every protected API call includes:
Authorization: Bearer <token>

The token is:
- Saved from /auth/login
- Added automatically in every protected request headers
- Removed during logout

### 6. Tested User Flows
The frontend and backend integration supports:
- Register user/admin
- Login user/admin
- Admin creates event (POST /admin/events)
- Event immediately appears because frontend fetches GET /events again
- User can register for event
- Admin can delete events
- All API responses are correctly shown on UI

DELIVERABLE 4
# API Documentation

This document explains all backend REST APIs implemented in the Event Management System.  
All APIs follow JSON format and use JWT-based authentication.

Base URL:
http://localhost:8080

-------------------------------------------------------------
# 1. Authentication APIs
-------------------------------------------------------------

## 1.1 Register User/Admin
POST /auth/register

Request Body:
{
  "username": "diya",
  "password": "1234",
  "role": "ADMIN"   // or "USER"
}

Success Response:
{
  "msg": "registered"
}

Error Response:
{
  "error": "exists"
}

-------------------------------------------------------------

## 1.2 Login User/Admin
POST /auth/login

Request Body:
{
  "username": "diya",
  "password": "1234"
}

Success Response:
{
  "token": "<jwt-token>",
  "role": "ADMIN"
}

Error Response:
{
  "error": "invalid"
}

-------------------------------------------------------------
# 2. Event APIs (Public + Admin + User)
-------------------------------------------------------------

## 2.1 Get All Events (Public)
GET /events

Response:
[
  {
    "id": "123",
    "title": "Tech Fest",
    "description": "Tech event",
    "category": "Tech",
    "location": "Pune",
    "dateTime": "2025-12-10T18:00:00",
    "seats": 50,
    "createdBy": "admin"
  }
]

Supports filter:
GET /events?category=Music

-------------------------------------------------------------
# 3. Admin APIs (Protected)
Authorization Header Required:
Authorization: Bearer <token>

-------------------------------------------------------------

## 3.1 Create Event
POST /admin/events

Request Body:
{
  "title": "Workshop",
  "description": "Basics",
  "category": "Workshop",
  "location": "Mumbai",
  "dateTime": "2025-12-10",
  "seats": 100
}

Success Response:
{
  "msg": "created"
}

Error Response:
{
  "error": "only admin can create"
}

-------------------------------------------------------------

## 3.2 Delete Event
DELETE /admin/events/{id}

Success Response:
{
  "msg": "deleted"
}

Error Response:
{
  "error": "only admin"
}

-------------------------------------------------------------

## 3.3 View Event Registrations
GET /admin/events/{id}/registrations

Response:
[
  {
    "id": "001",
    "eventId": "123",
    "username": "user1"
  }
]

Error:
{
  "error": "only admin"
}

-------------------------------------------------------------
# 4. User APIs (Protected)
Authorization Header Required:
Authorization: Bearer <token>

-------------------------------------------------------------

## 4.1 Register for an Event
POST /events/{id}/register

Success:
{
  "msg": "registered"
}

Error:
{
  "error": "full"
}

-------------------------------------------------------------

## 4.2 View My Registrations
GET /events/my

Response:
[
  {
    "id": "001",
    "eventId": "123",
    "username": "diya"
  }
]

DELIVERABLE 5

<img width="949" height="973" alt="Screenshot 2025-11-28 021504" src="https://github.com/user-attachments/assets/195babb1-eab6-4a11-879b-7f57bb87aa35" />

<img width="951" height="941" alt="Screenshot 2025-11-28 021818" src="https://github.com/user-attachments/assets/b206f096-7c5d-447e-8701-8bd9bbf4def0" />

<img width="940" height="902" alt="Screenshot 2025-11-28 021906" src="https://github.com/user-attachments/assets/6326faa0-07a8-4e4d-a7ec-4403f387d60b" />

<img width="940" height="950" alt="Screenshot 2025-11-28 021933" src="https://github.com/user-attachments/assets/db990d84-e85e-409d-aa7b-48f405376b99" />

<img width="938" height="960" alt="Screenshot 2025-11-28 022008" src="https://github.com/user-attachments/assets/800b8405-dd85-49d0-9d63-5c9db5ab30ad" />

