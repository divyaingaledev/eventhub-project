import React, { useState } from "react";
import axios from "axios";

function AddEvent() {
  const [event, setEvent] = useState({
    title: "",
    description: "",
    eventDate: "",
    eventTime: "",
    categoryId: "",
    venueId: "",
    availableSeats: "",
    ticketPrice: "",
    imageUrl: "",
    status: "UPCOMING",
  });

  const [message, setMessage] = useState("");

  const categories = [
    { id: 1, name: "Music" },
    { id: 2, name: "Sports" },
    { id: 3, name: "Technology" },
    { id: 4, name: "Business" },
    { id: 5, name: "Education" },
    { id: 6, name: "Cultural" },
    { id: 7, name: "Entertainment" },
    { id: 8, name: "Workshop" },
  ];

  const venues = [
    { id: 1, name: "Balewadi Stadium" },
    { id: 2, name: "Pune International Convention Centre" },
    { id: 3, name: "Yashwantrao Chavan Natyagruha" },
    { id: 4, name: "Auto Cluster Exhibition Center" },
    { id: 5, name: "City Pride" },
    { id: 6, name: "Grand Hall" },
  ];

  const handleChange = (e) => {
    const { name, value } = e.target;

    setEvent({
      ...event,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post("http://localhost:8080/api/events", {
        title: event.title,
        description: event.description,
        eventDate: event.eventDate,
        eventTime: event.eventTime,
        categoryId: Number(event.categoryId),
        venueId: Number(event.venueId),
        availableSeats: Number(event.availableSeats),
        ticketPrice: Number(event.ticketPrice),
        imageUrl: event.imageUrl,
        status: event.status,
      });

      console.log("Event created:", response.data);

      setMessage("Event published successfully!");

      setEvent({
        title: "",
        description: "",
        eventDate: "",
        eventTime: "",
        categoryId: "",
        venueId: "",
        availableSeats: "",
        ticketPrice: "",
        imageUrl: "",
        status: "UPCOMING",
      });
    } catch (error) {
      console.error("Error creating event:", error);
      setMessage("Failed to publish event. Please check backend.");
    }
  };

  return (
    <div className="container mt-4 mb-5">
      <div className="card shadow p-4">
        <h2 className="mb-4">Post a New Event</h2>

        {message && <div className="alert alert-info">{message}</div>}

        <form onSubmit={handleSubmit}>
          {/* Event Title */}
          <div className="mb-3">
            <label className="form-label">Event Title</label>

            <input
              type="text"
              name="title"
              className="form-control"
              placeholder="Enter event title"
              value={event.title}
              onChange={handleChange}
              required
            />
          </div>

          {/* Description */}
          <div className="mb-3">
            <label className="form-label">Description</label>

            <textarea
              name="description"
              className="form-control"
              rows="4"
              placeholder="Enter event description"
              value={event.description}
              onChange={handleChange}
              required
            ></textarea>
          </div>

          {/* Event Date */}
          <div className="mb-3">
            <label className="form-label">Event Date</label>

            <input
              type="date"
              name="eventDate"
              className="form-control"
              value={event.eventDate}
              onChange={handleChange}
              required
            />
          </div>

          {/* Event Time */}
          <div className="mb-3">
            <label className="form-label">Event Time</label>

            <input
              type="time"
              name="eventTime"
              className="form-control"
              value={event.eventTime}
              onChange={handleChange}
              required
            />
          </div>

          {/* Category */}
          <div className="mb-3">
            <label className="form-label">Category</label>

            <select
              name="categoryId"
              className="form-select"
              value={event.categoryId}
              onChange={handleChange}
              required
            >
              <option value="">Select Category</option>

              {categories.map((category) => (
                <option key={category.id} value={category.id}>
                  {category.name}
                </option>
              ))}
            </select>
          </div>

          {/* Venue */}
          <div className="mb-3">
            <label className="form-label">Venue</label>

            <select
              name="venueId"
              className="form-select"
              value={event.venueId}
              onChange={handleChange}
              required
            >
              <option value="">Select Venue</option>

              {venues.map((venue) => (
                <option key={venue.id} value={venue.id}>
                  {venue.name}
                </option>
              ))}
            </select>
          </div>

          {/* Available Seats */}
          <div className="mb-3">
            <label className="form-label">Total Available Seats</label>

            <input
              type="number"
              name="availableSeats"
              className="form-control"
              placeholder="Enter available seats"
              value={event.availableSeats}
              onChange={handleChange}
              min="1"
              required
            />
          </div>

          {/* Ticket Price */}
          <div className="mb-3">
            <label className="form-label">Ticket Price (₹)</label>

            <input
              type="number"
              name="ticketPrice"
              className="form-control"
              placeholder="Enter ticket price"
              value={event.ticketPrice}
              onChange={handleChange}
              min="0"
              required
            />
          </div>

          {/* Banner Image URL */}
          <div className="mb-3">
            <label className="form-label">Banner Image URL</label>

            <input
              type="url"
              name="imageUrl"
              className="form-control"
              placeholder="https://example.com/event.jpg"
              value={event.imageUrl}
              onChange={handleChange}
            />
          </div>

          {/* Image Preview */}
          {event.imageUrl && (
            <div className="mb-3">
              <img
                src={event.imageUrl}
                alt="Event Banner Preview"
                className="img-fluid rounded"
                style={{
                  maxHeight: "250px",
                  width: "100%",
                  objectFit: "cover",
                }}
              />
            </div>
          )}

          {/* Status */}
          <div className="mb-4">
            <label className="form-label">Status</label>

            <select
              name="status"
              className="form-select"
              value={event.status}
              onChange={handleChange}
              required
            >
              <option value="UPCOMING">UPCOMING</option>

              <option value="ONGOING">ONGOING</option>

              <option value="COMPLETED">COMPLETED</option>

              <option value="CANCELLED">CANCELLED</option>
            </select>
          </div>

          {/* Publish Event */}
          <button type="submit" className="btn btn-primary w-100">
            Publish Event
          </button>
        </form>
      </div>
    </div>
  );
}

export default AddEvent;
