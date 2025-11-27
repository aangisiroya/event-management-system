        # Event Management Fullstack (ZIP)

Backend: Spring Boot + MongoDB Atlas
Frontend: Vite + React

Run backend:
1. cd backend
2. mvn clean package
3. mvn spring-boot:run

Run frontend:
1. cd frontend
2. npm install
3. npm run dev

Default MongoDB connection in backend/application.properties uses admin:1234 on cluster0.pv1jiss and database 'eventapp'.

APIs:
GET /events
GET /events?category=Tech
POST /events (ADMIN only) {title,description,category,location,dateTime, seats}
DELETE /events/{id} (ADMIN only)
POST /events/{id}/register (USER)
GET /events/{id}/registrations (ADMIN)
GET /events/my (USER registrations)
