import { Navigate } from "react-router-dom";

export default function ProtectedRoute({ children, role }) {
  const token = localStorage.getItem("token");

  const rawRole = localStorage.getItem("role");

  if (!token) {
    return <Navigate to="/login" replace />;
  }

  const userRole = rawRole ? rawRole.replace(/^ROLE_/i, "").toUpperCase() : "";

  const targetRole = role ? role.replace(/^ROLE_/i, "").toUpperCase() : "";

  if (targetRole && userRole !== targetRole) {
    if (userRole === "ADMIN") {
      return <Navigate to="/admin/dashboard" replace />;
    }

    return <Navigate to="/user/dashboard" replace />;
  }

  return children;
}
