export default function Navbar() {
  return (
    <div
      style={{
        width: "100%",
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        padding: "16px 24px",
        backgroundColor: "white",
        borderBottom: "1px solid #eee",
      }}
    >
      <span style={{ fontWeight: "bold", fontSize: "22px" }}>SprintPrep</span>

      <span style={{ cursor: "pointer", fontSize: "18px" }}>Login</span>
    </div>
  );
}
