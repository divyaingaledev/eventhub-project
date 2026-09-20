import { useEffect, useState } from "react";
import api from "../services/api";
import EventCard from "../components/EventCard";

export default function Home() {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    api.get("/events")
      .then((res) => {
        const data = Array.isArray(res.data) ? res.data : res.data?.content || [];
        setEvents(data);
      })
      .catch(() => setError("Could not load events. Make sure the Spring Boot backend is running."))
      .finally(() => setLoading(false));
  }, []);

  return (
    <>
      <section className="hero">
        <div>
          <p className="eyebrow">DISCOVER • BOOK • EXPERIENCE</p>
          <h1>Find your next <span>great event.</span></h1>
          <p className="hero-text">
            Explore exciting events, discover new experiences and reserve your seats in a few clicks.
          </p>
          <a href="#events" className="primary-btn hero-btn">Explore Events</a>
        </div>
      </section>

      <section className="section" id="events">
        <div className="section-heading">
          <div>
            <p className="eyebrow">EVENTS</p>
            <h2>Upcoming Events</h2>
          </div>
          <p>Choose an event and reserve your spot.</p>
        </div>

        {loading && <div className="status">Loading events...</div>}
        {error && <div className="status error">{error}</div>}

        {!loading && !error && events.length === 0 && (
          <div className="status">No events found.</div>
        )}

        <div className="event-grid">
          {events.map((event) => <EventCard key={event.id} event={event} />)}
        </div>
      </section>
    </>
  );
}