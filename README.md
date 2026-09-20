# EventHub – Online Event Booking System

EventHub is a full-stack web application for managing and booking events.
Users can browse events and book tickets, while admins can manage events, categories, venues, and users.

## Technologies Used

* **Frontend:** React.js, JavaScript, HTML, CSS, Bootstrap
* **Backend:** Java, Spring Boot, Spring Security, REST API
* **Database:** MySQL
* **Authentication:** JWT
* **Payment:** Razorpay
* **Tools:** Git, GitHub, Maven, Postman

## User Functionalities

* Register a new account as a user.
* Login securely using email and password.
* Browse available and upcoming events.
* View complete event details such as date, time, venue, price, and available seats.
* Select the number of tickets to book.
* Book tickets for an event.
* Choose **Cash on Venue** or **Online Payment**.
* Make online payments using Razorpay.
* View booking confirmation and payment status.
* View all personal bookings from the User Dashboard.
* Access only user-related features through role-based authentication.

## Admin Functionalities

* Register and login as an administrator.
* Access a separate Admin Dashboard.
* Add new events.
* Update existing event information.
* Delete events when required.
* Manage event categories.
* Add and manage venues.
* Manage registered users.
* Set event date, time, ticket price, and available seats.
* View basic event and user information.
* Access admin features through role-based authorization.

## Authentication and Security

* JWT-based authentication.
* Passwords are encrypted using BCrypt.
* Role-based access for User and Admin.
* Protected APIs using Spring Security.
* Global exception handling for API errors.
* CORS configuration for frontend-backend communication.

## Event Booking Flow

1. User creates an account and logs in.
2. User browses available events.
3. User selects an event and views its details.
4. User selects the number of tickets.
5. User chooses a payment method.
6. Booking is created after the required payment process.
7. User can view the booking from the User Dashboard.

## Payment Options

### Cash on Venue

Users can select Cash on Venue while booking an event.

### Online Payment

Users can pay online through Razorpay.

The backend creates and verifies the Razorpay payment before completing the online payment flow.

# Project Structure

```text
EventHub/
│
├── README.md
│
├── eventhub-backend/
│
└── eventhub-frontend/
```

# Backend Structure

```text
eventhub-backend/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/eventhub/
│   │   │       │
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── entity/
│   │   │       ├── enums/
│   │   │       ├── exception/
│   │   │       ├── mapper/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       └── service/
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── pom.xml
├── Dockerfile
├── mvnw
└── mvnw.cmd
```

## Backend Modules

* **Controller:** Handles REST API requests.
* **Service:** Contains application business logic.
* **Repository:** Handles database operations.
* **Entity:** Represents database tables.
* **DTO:** Transfers data between frontend and backend.
* **Security:** Handles JWT authentication and authorization.
* **Exception:** Handles application errors.
* **Mapper:** Converts entities to DTOs and vice versa.
* **Config:** Contains application and security configuration.
* **Enums:** Contains predefined values such as roles, booking status, and payment status.

# Frontend Structure

```text
eventhub-frontend/
│
├── public/
│   └── _redirects
│
├── src/
│   │
│   ├── components/
│   │   ├── EventCard.jsx
│   │   ├── Footer.jsx
│   │   ├── Navbar.jsx
│   │   └── ProtectedRoute.jsx
│   │
│   ├── pages/
│   │   ├── AddEvent.jsx
│   │   ├── AdminDashboard.jsx
│   │   ├── EventDetails.jsx
│   │   ├── Home.jsx
│   │   ├── Login.jsx
│   │   ├── MyBookings.jsx
│   │   ├── NotFound.jsx
│   │   ├── Register.jsx
│   │   └── UserDashboard.jsx
│   │
│   ├── services/
│   │   ├── api.js
│   │   ├── authService.js
│   │   └── eventService.js
│   │
│   ├── styles/
│   │   ├── AdminDashboard.css
│   │   └── Login.css
│   │
│   ├── App.jsx
│   ├── main.jsx
│   └── index.css
│
├── .env.example
├── .gitignore
├── index.html
├── package.json
├── package-lock.json
└── vite.config.js
```

## Frontend Modules

* **Components:** Reusable UI components such as Navbar, Footer, Event Card, and Protected Route.
* **Pages:** Contains the main application screens for users and admins.
* **Services:** Handles API communication, authentication, and event-related requests.
* **Styles:** Contains CSS files used to design different pages.
* **App.jsx:** Defines the main application routes and page navigation.
* **main.jsx:** Entry point of the React application.
* **index.css:** Contains common application styles.
* **Public:** Contains files that are directly served by the frontend.
* **vite.config.js:** Vite configuration for the React application.

## Main Frontend Pages

### User Pages

* Home
* Login
* Register
* Event Details
* My Bookings
* User Dashboard

### Admin Pages

* Admin Dashboard
* Add Event
* Event Management

### Common Pages

* Navbar
* Footer
* Not Found

## Main APIs

### Authentication

```text
POST /api/auth/register
POST /api/auth/login
```

### Events

```text
GET    /api/events
GET    /api/events/{id}
POST   /api/events
PUT    /api/events/{id}
DELETE /api/events/{id}
```

### Bookings

```text
POST /api/bookings
GET  /api/bookings/my
```

### Categories

```text
GET    /api/categories
POST   /api/categories
PUT    /api/categories/{id}
DELETE /api/categories/{id}
```

### Venues

```text
GET    /api/venues
POST   /api/venues
PUT    /api/venues/{id}
DELETE /api/venues/{id}
```

### Payments

```text
POST /api/payments/create-order/{bookingId}
POST /api/payments/verify
```

## How to Run the Project

### Backend

Go to the backend folder:

```bash
cd eventhub-backend
```

For Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

### Frontend

Open another terminal and go to:

```bash
cd eventhub-frontend
```

Install dependencies:

```bash
npm install
```

Start the application:

```bash
npm run dev
```

Frontend runs on:

```text
http://localhost:5173
```

## Important Notes

* MySQL is required to run the backend.
* Database configuration should be provided through environment variables.
* JWT and Razorpay secret keys should not be uploaded to GitHub.
* The frontend communicates with the backend using REST APIs.

## Developer

**Divya Ingale**

Java Developer | Full Stack Developer

**Skills:** Java, Spring Boot, Spring Security, Hibernate, REST API, React.js, JavaScript, MySQL, Git, GitHub, Maven, Postman
