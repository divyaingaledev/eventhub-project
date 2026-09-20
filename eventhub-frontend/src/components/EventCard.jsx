import { Link } from "react-router-dom";

export default function EventCard({ event }) {
  const image =
    event.imageUrl ||
    event.image ||
    "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?auto=format&fit=crop&w=900&q=80";

  return (
    <article className="event-card">
      <img src={image} alt={event.title || "Event"} />
      <div className="event-card-body">
        <span className="event-badge">{event.categoryName || event.category || "Event"}</span>
        <h3>{event.title || event.name || "Untitled Event"}</h3>
        <p> {event.location || event.venueName || "Location not available"}</p>
        <p> {event.date || event.eventDate || "Date not available"}</p>
        <Link className="primary-btn" to={`/event/${event.id}`}>
          View Details
        </Link>
      </div>
    </article>
  );
}