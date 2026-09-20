import { Link } from "react-router-dom";

export default function NotFound() {
  return (
    <section className="status not-found">
      <h1>404</h1>
      <p>Page not found.</p>
      <Link className="primary-btn" to="/">Go Home</Link>
    </section>
  );
}