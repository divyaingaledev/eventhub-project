import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../services/api";

export default function UserDashboard() {
  const name = localStorage.getItem("name");
  const [events, setEvents] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    api
      .get("/events")
      .then((res) => {
        setEvents(res.data);
      })
      .catch((err) => {
        console.error("Failed to fetch events", err);
      });
  }, []);

  return (
    <section className="dashboard">
      <div className="dashboard-header">
        <div>
          <p className="eyebrow">USER DASHBOARD</p>

          <h1>Welcome, {name}</h1>

          <p>Discover events and manage your bookings.</p>
        </div>
      </div>

      <div className="dashboard-grid" style={{ marginBottom: "2rem" }}>
        <Link to="/bookings" className="dashboard-card">
          <h3>My Bookings</h3>

          <p>View your event bookings.</p>

          <span className="primary-btn">View Bookings</span>
        </Link>
      </div>

      <h2>Explore Upcoming Events</h2>

      <div className="dashboard-grid" style={{ marginTop: "1rem" }}>
        {events.length === 0 ? (
          <p>No active events available right now.</p>
        ) : (
          events.map((event) => (
            <div key={event.id} className="dashboard-card">
              <h3>{event.title || event.name}</h3>

              <p>
                {event.venueName || event.location || "Venue details inside"}
              </p>

              <p>{event.eventDate || event.date || "Date not available"}</p>

              <button
                className="primary-btn"
                style={{ marginTop: "1rem" }}
                onClick={() => navigate(`/event/${event.id}`)}
              >
                View & Book
              </button>
            </div>
          ))
        )}
      </div>
    </section>
  );
}
