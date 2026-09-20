# EventHub Frontend

React frontend for the EventHub Spring Boot backend.

## Technologies
- React
- Vite
- Axios
- React Router DOM
- CSS
- JWT authentication

## Run

```bash
npm install
npm run dev
```

Default frontend:
http://localhost:5173

Default backend API:
http://localhost:8080/api

If your backend uses another URL, create `.env`:

```env
VITE_API_BASE_URL=http://localhost:8080/api
```

## Main API integration

- POST `/auth/login`
- GET `/events`
- GET `/events/{id}`
- POST `/bookings`
- GET `/bookings/my`

The booking request currently sends:

```json
{
  "eventId": 1,
  "seats": 2
}
```

If your Spring Boot Booking DTO uses different field names, update `src/pages/EventDetails.jsx`.
