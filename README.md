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
