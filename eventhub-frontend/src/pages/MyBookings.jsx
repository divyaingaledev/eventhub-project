import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../services/api";

export default function MyBookings() {
  const [bookings, setBookings] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    api.get("/bookings/my")
      .then((res) => {
        const data = Array.isArray(res.data) ? res.data : res.data?.content || [];
        setBookings(data);
      })
      .catch(() => setError("Unable to load your bookings."));
  }, []);

  return (
    <section className="section bookings-page">
      <p className="eyebrow">ACCOUNT</p>
      <h1>My Bookings</h1>

      {error && <div className="status error">{error}</div>}

      {!error && bookings.length === 0 && (
        <div className="empty">
          <h3>No bookings yet</h3>
          <p>Explore events and reserve your first experience.</p>
          <Link className="primary-btn" to="/">Explore Events</Link>
        </div>
      )}

      <div className="booking-list">
        {bookings.map((booking) => (
          <div className="booking-row" key={booking.id}>
            <div>
              <h3>{booking.eventName || booking.event?.title || `Booking #${booking.id}`}</h3>
              <p>Booking ID: {booking.id}</p>
            </div>
            <span className="status-pill">{booking.status || "BOOKED"}</span>
          </div>
        ))}
      </div>
    </section>
  );
}