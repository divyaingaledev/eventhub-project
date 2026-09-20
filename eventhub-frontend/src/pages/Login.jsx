import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../services/api";

export default function Login() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;

    setForm((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const handleLogin = async (e) => {
    e.preventDefault();

    setError("");
    setLoading(true);

    try {
      // Remove old authentication data
      localStorage.removeItem("token");
      localStorage.removeItem("role");
      localStorage.removeItem("userId");
      localStorage.removeItem("name");
      localStorage.removeItem("email");

      const response = await api.post("/auth/login", form);

      const data = response.data;

      console.log("Login response:", data);

      if (!data.token) {
        throw new Error("JWT token was not returned");
      }

      const cleanRole = String(data.role || "")
        .replace(/^ROLE_/i, "")
        .toUpperCase();

      if (cleanRole !== "USER" && cleanRole !== "ADMIN") {
        setError("Invalid user role received from backend.");

        return;
      }

      // Save JWT
      localStorage.setItem("token", data.token);

      // Save role
      localStorage.setItem("role", cleanRole);

      // Save user information
      localStorage.setItem("userId", data.userId || "");

      localStorage.setItem("name", data.name || "");

      localStorage.setItem("email", data.email || "");

      console.log("JWT saved successfully");

      console.log("Logged in role:", cleanRole);

      if (cleanRole === "ADMIN") {
        navigate("/admin/dashboard", { replace: true });
      } else {
        navigate("/user/dashboard", { replace: true });
      }
    } catch (err) {
      console.error("Login error:", err);

      console.error("Backend response:", err.response?.data);

      const data = err.response?.data;

      if (data?.message) {
        setError(data.message);
      } else if (typeof data === "string") {
        setError(data);
      } else {
        setError("Invalid email or password.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="login-page">
      <form className="login-form" onSubmit={handleLogin}>
        <p className="eyebrow">EVENTHUB</p>

        <h1>Welcome Back</h1>

        <p className="muted">Login to your EventHub account</p>

        {error && <div className="form-error">{error}</div>}

        <div className="input-group">
          <label htmlFor="email">Email</label>

          <input
            id="email"
            type="email"
            name="email"
            placeholder="Enter your email"
            value={form.email}
            onChange={handleChange}
            required
          />
        </div>

        <div className="input-group">
          <label htmlFor="password">Password</label>

          <input
            id="password"
            type="password"
            name="password"
            placeholder="Enter your password"
            value={form.password}
            onChange={handleChange}
            required
          />
        </div>

        <button type="submit" className="login-btn" disabled={loading}>
          {loading ? "Logging in..." : "Login"}
        </button>

        <p className="auth-link">
          Don't have an account? <Link to="/register">Register</Link>
        </p>
      </form>
    </section>
  );
}
