import { Link, useNavigate } from "react-router-dom";

export default function Navbar() {
  const navigate = useNavigate();

  const token = localStorage.getItem("token");

  const role = localStorage.getItem("role");

  const name = localStorage.getItem("name");

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("userId");
    localStorage.removeItem("name");
    localStorage.removeItem("email");

    navigate("/");
  };

  const dashboard = role === "ADMIN" ? "/admin/dashboard" : "/user/dashboard";

  return (
    <header className="navbar">
      <Link className="brand" to="/">
        <span>Event</span>Hub
      </Link>

      <nav>
        <Link to="/">Home</Link>

        {token && <Link to={dashboard}>Dashboard</Link>}

        {token && role === "USER" && <Link to="/bookings">My Bookings</Link>}

        {!token ? (
          <>
            <Link to="/login">Login</Link>

            <Link className="nav-btn" to="/register">
              Register
            </Link>
          </>
        ) : (
          <>
            <span className="nav-user">{name}</span>

            <button className="nav-btn" onClick={logout}>
              Logout
            </button>
          </>
        )}
      </nav>
    </header>
  );
}
