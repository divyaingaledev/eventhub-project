import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../services/api";

export default function EventDetails() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [event, setEvent] = useState(null);
  const [seats, setSeats] = useState(1);
  const [paymentMethod, setPaymentMethod] = useState("CASH_ON_VENUE");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    loadEvent();
  }, [id]);

  const loadEvent = async () => {
    try {
      const response = await api.get(`/events/${id}`);
      setEvent(response.data);
    } catch (error) {
      console.error("Event loading error:", error);
      setMessage("Unable to load event details.");
    }
  };

  const handleSeatsChange = (e) => {
    let quantity = Number(e.target.value);

    if (quantity < 1) {
      quantity = 1;
    }

    if (event && quantity > event.availableSeats) {
      quantity = event.availableSeats;
    }

    setSeats(quantity);
  };

  const showError = (error, defaultMessage) => {
    console.error(error);

    const errorMessage =
      error.response?.data?.message ||
      error.response?.data?.error ||
      (typeof error.response?.data === "string" ? error.response.data : null) ||
      error.message ||
      defaultMessage;

    setMessage(errorMessage);
    setSuccess(false);
  };

  const openRazorpayCheckout = (order, booking) => {
    if (!window.Razorpay) {
      setMessage("Razorpay Checkout is not loaded. Please check index.html.");
      setSuccess(false);
      setLoading(false);
      return;
    }

    const options = {
      key: order.keyId,
      amount: order.amount,
      currency: order.currency,
      name: "EventHub",
      description: event.title || event.name || "Event Booking",
      order_id: order.orderId,

      handler: async function (response) {
        try {
          setLoading(true);
          setMessage("Verifying payment...");
          setSuccess(false);

          const verifyResponse = await api.post("/payments/verify", {
            bookingId: booking.id,
            razorpayOrderId: response.razorpay_order_id,
            razorpayPaymentId: response.razorpay_payment_id,
            razorpaySignature: response.razorpay_signature,
          });

          const verifiedBooking = verifyResponse.data;

          setSuccess(true);
          setMessage(
            `Payment Successful! Booking ${
              verifiedBooking.bookingReference ||
              booking.bookingReference ||
              booking.id
            } is confirmed.`,
          );

          setTimeout(() => {
            navigate("/bookings");
          }, 2000);
        } catch (error) {
          showError(
            error,
            "Payment verification failed. Please contact support.",
          );
        } finally {
          setLoading(false);
        }
      },

      prefill: {
        name: localStorage.getItem("name") || "",
        email: localStorage.getItem("email") || "",
      },

      notes: {
        bookingId: String(booking.id),
        eventId: String(id),
      },

      theme: {
        color: "#2563eb",
      },

      modal: {
        ondismiss: function () {
          setLoading(false);
          setMessage(
            "Payment window was closed. Your booking is still pending.",
          );
          setSuccess(false);
        },
      },
    };

    const razorpay = new window.Razorpay(options);

    razorpay.on("payment.failed", function (response) {
      setLoading(false);
      setSuccess(false);

      const reason =
        response.error?.description ||
        response.error?.reason ||
        "Payment failed.";

      setMessage(`Payment Failed: ${reason}`);
    });

    razorpay.open();
  };

  const book = async () => {
    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/login");
      return;
    }

    if (!event) {
      setMessage("Event details are not available.");
      return;
    }

    const ticketQuantity = Number(seats);
    const ticketPrice = Number(event.ticketPrice ?? event.price ?? 0);
    const totalAmount = ticketPrice * ticketQuantity;

    if (ticketQuantity < 1) {
      setMessage("Please select at least 1 ticket.");
      setSuccess(false);
      return;
    }

    if (ticketQuantity > event.availableSeats) {
      setMessage(`Only ${event.availableSeats} tickets are available.`);
      setSuccess(false);
      return;
    }

    if (ticketPrice <= 0) {
      setMessage("Ticket price is not available.");
      setSuccess(false);
      return;
    }

    setLoading(true);
    setMessage("");
    setSuccess(false);

    try {
      const bookingResponse = await api.post("/bookings", {
        eventId: Number(id),
        ticketQuantity: ticketQuantity,
      });

      const booking = bookingResponse.data;

      if (!booking || !booking.id) {
        throw new Error("Booking was not created.");
      }

      if (paymentMethod === "ONLINE") {
        setMessage("Creating secure payment...");

        const orderResponse = await api.post(
          `/payments/create-order/${booking.id}`,
        );

        const razorpayOrder = orderResponse.data;

        if (!razorpayOrder || !razorpayOrder.orderId) {
          throw new Error("Unable to create Razorpay order.");
        }

        setLoading(false);

        openRazorpayCheckout(razorpayOrder, booking);

        return;
      }

      setSuccess(true);
      setMessage(
        `Booking Successful! Booking ${
          booking.bookingReference || booking.id
        } has been created. Total Amount: ₹${totalAmount}. Please pay at the venue.`,
      );

      setTimeout(() => {
        navigate("/bookings");
      }, 2500);
    } catch (error) {
      showError(error, "Booking failed. Please try again.");
      setLoading(false);
    }
  };

  if (!event) {
    return <div className="status">{message || "Loading event..."}</div>;
  }

  const image =
    event.imageUrl ||
    event.image ||
    "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?auto=format&fit=crop&w=1200&q=80";

  const ticketPrice = Number(event.ticketPrice ?? event.price ?? 0);

  const ticketQuantity = Number(seats || 0);

  const totalAmount = ticketPrice * ticketQuantity;

  return (
    <section className="details-page">
      <img
        className="details-image"
        src={image}
        alt={event.title || event.name || "Event"}
      />

      <div className="details-content">
        <span className="event-badge">
          {event.categoryName || event.category || "Event"}
        </span>

        <h1>{event.title || event.name}</h1>

        <p className="details-description">
          {event.description || "Experience an amazing event with EventHub."}
        </p>

        <div className="info-list">
          <p>
            <strong>Location:</strong>{" "}
            {event.venueName || event.location || "N/A"}
          </p>

          <p>
            <strong>Date:</strong> {event.eventDate || "N/A"}
          </p>

          <p>
            <strong>Time:</strong> {event.eventTime || "N/A"}
          </p>

          <p>
            <strong>Available Tickets:</strong> {event.availableSeats ?? 0}
          </p>

          <p>
            <strong>Price per Ticket:</strong> ₹{ticketPrice}
          </p>
        </div>

        <div className="booking-box">
          <div className="form-group">
            <label>
              <strong>Number of Tickets:</strong>
            </label>

            <input
              type="number"
              min="1"
              max={event.availableSeats || 1}
              value={seats}
              onChange={handleSeatsChange}
              disabled={loading || event.availableSeats === 0}
            />
          </div>

          <div className="form-group" style={{ margin: "1rem 0" }}>
            <label>
              <strong>Select Payment Method:</strong>
            </label>

            <div
              style={{
                display: "flex",
                gap: "1.5rem",
                marginTop: "0.7rem",
                flexWrap: "wrap",
              }}
            >
              <label style={{ cursor: "pointer" }}>
                <input
                  type="radio"
                  name="payment"
                  value="CASH_ON_VENUE"
                  checked={paymentMethod === "CASH_ON_VENUE"}
                  onChange={(e) => setPaymentMethod(e.target.value)}
                  disabled={loading}
                />{" "}
                Cash on Venue
              </label>

              <label style={{ cursor: "pointer" }}>
                <input
                  type="radio"
                  name="payment"
                  value="ONLINE"
                  checked={paymentMethod === "ONLINE"}
                  onChange={(e) => setPaymentMethod(e.target.value)}
                  disabled={loading}
                />{" "}
                Pay Online
              </label>
            </div>
          </div>

          <div
            style={{
              marginTop: "1rem",
              padding: "1rem",
              borderRadius: "8px",
              background: "#f5f5f5",
            }}
          >
            <p>
              <strong>Price per Ticket:</strong> ₹{ticketPrice}
            </p>

            <p>
              <strong>Number of Tickets:</strong> {ticketQuantity}
            </p>

            <h3>Total Amount: ₹{totalAmount}</h3>
          </div>

          <button
            className="primary-btn"
            onClick={book}
            disabled={loading || event.availableSeats === 0}
          >
            {loading
              ? "Processing..."
              : paymentMethod === "ONLINE"
                ? "Proceed to Payment"
                : "Confirm Booking"}
          </button>
        </div>

        {message && (
          <div
            className="booking-message"
            style={{
              marginTop: "1rem",
              padding: "1rem",
              borderRadius: "8px",
              fontWeight: "600",
              background: success ? "#d4edda" : "#f8d7da",
              color: success ? "#155724" : "#721c24",
              border: success ? "1px solid #c3e6cb" : "1px solid #f5c6cb",
            }}
          >
            {message}
          </div>
        )}
      </div>
    </section>
  );
}
