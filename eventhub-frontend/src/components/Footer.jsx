export default function Footer() {
  return (
    <footer className="footer">
      <h3>EventHub</h3>
      <p>Discover events. Book experiences. Make memories.</p>
      <small>© {new Date().getFullYear()} EventHub. All rights reserved.</small>
    </footer>
  );
}