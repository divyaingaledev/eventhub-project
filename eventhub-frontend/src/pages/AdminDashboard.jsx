import { useEffect, useState } from "react";
import api from "../services/api";
import "../styles/AdminDashboard.css";

export default function AdminDashboard() {
  const name = localStorage.getItem("name") || "Admin";

  const [events, setEvents] = useState([]);
  const [users, setUsers] = useState([]);
  const [activeTab, setActiveTab] = useState("events");

  const [categories, setCategories] = useState([]);
  const [venues, setVenues] = useState([]);

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [loading, setLoading] = useState(false);
  const [editingId, setEditingId] = useState(null);

  const [eventForm, setEventForm] = useState({
    title: "",
    description: "",
    eventDate: "",
    eventTime: "",
    availableSeats: 100,
    ticketPrice: 0,
    imageUrl: "",
    categoryId: "",
    venueId: "",
    status: "UPCOMING",
  });

  useEffect(() => {
    loadEvents();
    loadUsers();
    loadCategories();
    loadVenues();
  }, []);

  const loadEvents = async () => {
    try {
      const response = await api.get("/events");
      setEvents(response.data);
    } catch (err) {
      console.error(err);
      setError("Unable to load events.");
    }
  };

  const loadUsers = async () => {
    try {
      const response = await api.get("/users");
      setUsers(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  const loadCategories = async () => {
    try {
      const response = await api.get("/categories");
      setCategories(response.data);
    } catch (err) {
      console.error("Category API error:", err);
    }
  };

  const loadVenues = async () => {
    try {
      const response = await api.get("/venues");
      setVenues(response.data);
    } catch (err) {
      console.error("Venue API error:", err);
    }
  };

  const handleFormChange = (e) => {
    const { name, value } = e.target;

    setEventForm((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const resetForm = () => {
    setEventForm({
      title: "",
      description: "",
      eventDate: "",
      eventTime: "",
      availableSeats: 100,
      ticketPrice: 0,
      imageUrl: "",
      categoryId: "",
      venueId: "",
      status: "UPCOMING",
    });

    setEditingId(null);
  };

  const handleCreateEvent = async (e) => {
    e.preventDefault();

    setError("");
    setSuccess("");
    setLoading(true);

    try {
      const eventData = {
        title: eventForm.title,
        description: eventForm.description,
        eventDate: eventForm.eventDate,
        eventTime: eventForm.eventTime,
        availableSeats: Number(eventForm.availableSeats),
        ticketPrice: Number(eventForm.ticketPrice),
        imageUrl: eventForm.imageUrl,
        categoryId: Number(eventForm.categoryId),
        venueId: Number(eventForm.venueId),
        status: eventForm.status,
      };

      if (editingId) {
        await api.put(`/events/${editingId}`, eventData);
        setSuccess("Event updated successfully.");
      } else {
        await api.post("/events", eventData);
        setSuccess("Event created successfully.");
      }

      resetForm();
      await loadEvents();
      setActiveTab("events");
    } catch (err) {
      console.error("Event save error:", err);

      setError(
        err.response?.data?.message ||
          "Failed to save event. Please check your data.",
      );
    } finally {
      setLoading(false);
    }
  };

  const handleEditEvent = (event) => {
    setEditingId(event.id);

    setEventForm({
      title: event.title || "",
      description: event.description || "",
      eventDate: event.eventDate || "",
      eventTime: event.eventTime || "",
      availableSeats: event.availableSeats ?? 0,
      ticketPrice: event.ticketPrice ?? 0,
      imageUrl: event.imageUrl || "",
      categoryId: event.categoryId || "",
      venueId: event.venueId || "",
      status: event.status || "UPCOMING",
    });

    setActiveTab("add-event");

    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  const handleDeleteEvent = async (id) => {
    if (!window.confirm("Are you sure you want to delete this event?")) {
      return;
    }

    try {
      await api.delete(`/events/${id}`);

      setSuccess("Event deleted successfully.");

      await loadEvents();
    } catch (err) {
      console.error(err);

      setError(err.response?.data?.message || "Failed to delete event.");
    }
  };

  return (
    <section className="admin-dashboard">
      <div className="dashboard-header">
        <p className="eyebrow">ADMIN CONTROL PANEL</p>

        <h1>Welcome, {name} 👋</h1>

        <p>
          Create, view, update, and manage all events and users in EventHub.
        </p>
      </div>

      <div className="stats-grid">
        <div className="stat-card">
          <span className="stat-icon">🎫</span>

          <div>
            <h2>{events.length}</h2>
            <p>Total Events</p>
          </div>
        </div>

        <div className="stat-card">
          <span className="stat-icon">👥</span>

          <div>
            <h2>{users.length}</h2>
            <p>Registered Users</p>
          </div>
        </div>

        <div className="stat-card">
          <span className="stat-icon">⚡</span>

          <div>
            <h2>Active</h2>
            <p>System Status</p>
          </div>
        </div>
      </div>

      {error && <div className="alert alert-error">{error}</div>}

      {success && <div className="alert alert-success">{success}</div>}

      <div className="dashboard-grid">
        <div className="dashboard-card" onClick={() => setActiveTab("events")}>
          <span>🎫</span>
          <h3>Manage Events</h3>
          <p>View, update, and delete events.</p>
        </div>

        <div
          className="dashboard-card"
          onClick={() => {
            resetForm();
            setActiveTab("add-event");
          }}
        >
          <span>➕</span>
          <h3>Add Event</h3>
          <p>Publish a new event.</p>
        </div>

        <div className="dashboard-card" onClick={() => setActiveTab("users")}>
          <span>👥</span>
          <h3>Registered Users</h3>
          <p>View registered users.</p>
        </div>
      </div>

      {activeTab === "events" && (
        <div className="admin-section">
          <div className="section-header">
            <h2>All Published Events ({events.length})</h2>

            <button
              className="primary-btn"
              onClick={() => {
                resetForm();
                setActiveTab("add-event");
              }}
            >
              + Create New Event
            </button>
          </div>

          {events.length === 0 ? (
            <p className="empty-state">No events found.</p>
          ) : (
            <div className="events-table-wrapper">
              <table className="admin-table">
                <thead>
                  <tr>
                    <th>Image</th>
                    <th>Title</th>
                    <th>Venue</th>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Seats</th>
                    <th>Price</th>
                    <th>Status</th>
                    <th>Action</th>
                  </tr>
                </thead>

                <tbody>
                  {events.map((event) => (
                    <tr key={event.id}>
                      <td>
                        <img
                          src={
                            event.imageUrl ||
                            "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?auto=format&fit=crop&w=200&q=80"
                          }
                          alt={event.title}
                          className="table-img"
                        />
                      </td>

                      <td>
                        <strong>{event.title}</strong>
                      </td>

                      <td>📍 {event.venueName || "N/A"}</td>

                      <td>📅 {event.eventDate || "N/A"}</td>

                      <td>🕐 {event.eventTime || "N/A"}</td>

                      <td>🎟️ {event.availableSeats ?? "N/A"}</td>

                      <td>₹{event.ticketPrice ?? 0}</td>

                      <td>
                        <span className="badge">
                          {event.status || "UPCOMING"}
                        </span>
                      </td>

                      <td>
                        <button
                          className="edit-btn"
                          onClick={() => handleEditEvent(event)}
                        >
                          Edit
                        </button>

                        <button
                          className="delete-btn"
                          onClick={() => handleDeleteEvent(event.id)}
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      )}

      {activeTab === "add-event" && (
        <div className="admin-section">
          <div className="section-header">
            <h2>{editingId ? "Update Event" : "Post a New Event"}</h2>

            {editingId && (
              <button className="secondary-btn" onClick={resetForm}>
                Cancel Edit
              </button>
            )}
          </div>

          <form onSubmit={handleCreateEvent} className="admin-form">
            <div className="form-group">
              <label>Event Title</label>

              <input
                type="text"
                name="title"
                placeholder="e.g. Java Full Stack Workshop"
                required
                value={eventForm.title}
                onChange={handleFormChange}
              />
            </div>

            <div className="form-group">
              <label>Description</label>

              <textarea
                name="description"
                rows="4"
                placeholder="Enter event description"
                required
                value={eventForm.description}
                onChange={handleFormChange}
              />
            </div>

            <div className="form-row">
              <div className="form-group">
                <label>Event Date</label>

                <input
                  type="date"
                  name="eventDate"
                  required
                  value={eventForm.eventDate}
                  onChange={handleFormChange}
                />
              </div>

              <div className="form-group">
                <label>Event Time</label>

                <input
                  type="time"
                  name="eventTime"
                  required
                  value={eventForm.eventTime}
                  onChange={handleFormChange}
                />
              </div>
            </div>

            <div className="form-row">
              <div className="form-group">
                <label>Category</label>

                <select
                  name="categoryId"
                  required
                  value={eventForm.categoryId}
                  onChange={handleFormChange}
                >
                  <option value="">Select Category</option>

                  {categories.length > 0 ? (
                    categories.map((category) => (
                      <option key={category.id} value={category.id}>
                        {category.name}
                      </option>
                    ))
                  ) : (
                    <>
                      <option value="1">Technology</option>
                      <option value="2">Music</option>
                      <option value="3">Sports</option>
                      <option value="4">Business</option>
                      <option value="5">Education</option>
                    </>
                  )}
                </select>
              </div>

              <div className="form-group">
                <label>Venue</label>

                <select
                  name="venueId"
                  required
                  value={eventForm.venueId}
                  onChange={handleFormChange}
                >
                  <option value="">Select Venue</option>

                  {venues.length > 0 ? (
                    venues.map((venue) => (
                      <option key={venue.id} value={venue.id}>
                        {venue.name}
                      </option>
                    ))
                  ) : (
                    <>
                      <option value="1">Bal Gandharva Rang Mandir</option>

                      <option value="2">
                        Pune International Exhibition Center
                      </option>

                      <option value="3">Yashwantrao Chavan Academy</option>

                      <option value="4">Dhole Patil College Auditorium</option>

                      <option value="5">Phoenix Marketcity Pune</option>
                    </>
                  )}
                </select>
              </div>
            </div>

            <div className="form-row">
              <div className="form-group">
                <label>Total Available Seats</label>

                <input
                  type="number"
                  name="availableSeats"
                  min="1"
                  required
                  value={eventForm.availableSeats}
                  onChange={handleFormChange}
                />
              </div>

              <div className="form-group">
                <label>Ticket Price (₹)</label>

                <input
                  type="number"
                  name="ticketPrice"
                  min="0"
                  step="0.01"
                  required
                  value={eventForm.ticketPrice}
                  onChange={handleFormChange}
                />
              </div>
            </div>

            <div className="form-group">
              <label>Banner Image URL</label>

              <input
                type="url"
                name="imageUrl"
                placeholder="https://images.unsplash.com/..."
                value={eventForm.imageUrl}
                onChange={handleFormChange}
              />
            </div>

            <div className="form-group">
              <label>Status</label>

              <select
                name="status"
                value={eventForm.status}
                onChange={handleFormChange}
              >
                <option value="UPCOMING">UPCOMING</option>

                <option value="ONGOING">ONGOING</option>

                <option value="COMPLETED">COMPLETED</option>

                <option value="CANCELLED">CANCELLED</option>
              </select>
            </div>

            <button
              type="submit"
              className="primary-btn submit-btn"
              disabled={loading}
            >
              {loading
                ? "Saving..."
                : editingId
                  ? "Update Event"
                  : "Publish Event"}
            </button>
          </form>
        </div>
      )}

      {activeTab === "users" && (
        <div className="admin-section">
          <h2>Registered Users ({users.length})</h2>

          {users.length === 0 ? (
            <p className="empty-state">No user records loaded.</p>
          ) : (
            <div className="events-table-wrapper">
              <table className="admin-table">
                <thead>
                  <tr>
                    <th>User ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                  </tr>
                </thead>

                <tbody>
                  {users.map((user) => (
                    <tr key={user.id}>
                      <td>#{user.id}</td>

                      <td>{user.fullName || user.name || "N/A"}</td>

                      <td>{user.email || "N/A"}</td>

                      <td>
                        <span className="badge">{user.role}</span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      )}
    </section>
  );
}
