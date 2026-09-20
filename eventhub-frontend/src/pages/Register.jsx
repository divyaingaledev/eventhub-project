import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../services/api";

export default function Register() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    fullName: "",
    email: "",
    password: "",
    mobile: "",
    role: "USER",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;

    setForm((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const handleRegister = async (e) => {
    e.preventDefault();

    setError("");
    setSuccess("");
    setLoading(true);

    try {
      const response = await api.post("/auth/register", {
        fullName: form.fullName,
        email: form.email,
        password: form.password,
        mobile: form.mobile,
        role: form.role,
      });

      console.log("Registration response:", response.data);

      setSuccess(
        `Registration successful as ${form.role}. Redirecting to login...`,
      );

      setTimeout(() => {
        navigate("/login", {
          replace: true,
        });
      }, 1500);
    } catch (err) {
      console.error("Registration error:", err);
      console.error("Backend response:", err.response?.data);

      const data = err.response?.data;

      if (data?.errors) {
        const messages =
          typeof data.errors === "object"
            ? Object.values(data.errors)
            : [data.errors];

        setError(messages.join(", "));
      } else if (data?.message) {
        setError(data.message);
      } else if (typeof data === "string") {
        setError(data);
      } else if (err.response?.status === 409) {
        setError("Email already registered.");
      } else if (err.response?.status === 400) {
        setError(
          "Invalid registration details. Please check your information.",
        );
      } else {
        setError(
          "Cannot connect to backend server. Ensure Spring Boot is running.",
        );
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="auth-page">
      <form className="auth-card" onSubmit={handleRegister}>
        <p className="eyebrow">EVENTHUB</p>

        <h1>Create Account</h1>

        <p className="muted">Register for EventHub</p>

        {error && <div className="form-error">{error}</div>}

        {success && <div className="form-success">{success}</div>}

        <label>Full Name</label>

        <input
          type="text"
          name="fullName"
          placeholder="Enter full name"
          value={form.fullName}
          onChange={handleChange}
          required
        />

        <label>Email</label>

        <input
          type="email"
          name="email"
          placeholder="Enter email"
          value={form.email}
          onChange={handleChange}
          required
        />

        <label>Mobile Number</label>

        <input
          type="tel"
          name="mobile"
          placeholder="9876543210"
          value={form.mobile}
          onChange={handleChange}
          maxLength="10"
          pattern="[6-9][0-9]{9}"
          required
        />

        <label>Password</label>

        <input
          type="password"
          name="password"
          placeholder="Minimum 6 characters"
          value={form.password}
          onChange={handleChange}
          minLength="6"
          required
        />

        <label>Register As</label>

        <div className="role-options">
          <label className="role-card">
            <input
              type="radio"
              name="role"
              value="USER"
              checked={form.role === "USER"}
              onChange={handleChange}
            />

            <div>
              <strong>User</strong>

              <small>Browse and book events</small>
            </div>
          </label>

          <label className="role-card">
            <input
              type="radio"
              name="role"
              value="ADMIN"
              checked={form.role === "ADMIN"}
              onChange={handleChange}
            />

            <div>
              <strong>Admin</strong>

              <small>Manage EventHub events</small>
            </div>
          </label>
        </div>

        <button type="submit" className="primary-btn full" disabled={loading}>
          {loading ? "Creating Account..." : "Create Account"}
        </button>

        <p className="auth-link">
          Already have an account? <Link to="/login">Login</Link>
        </p>
      </form>
    </section>
  );
}
