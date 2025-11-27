import React, { useState, useEffect } from "react";
import axios from "axios";
import dayjs from "dayjs";

const API = import.meta.env.VITE_API || "http://localhost:8080";

export default function App() {
  const [token, setToken] = useState(localStorage.getItem("token") || "");
  const [role, setRole] = useState(localStorage.getItem("role") || "");

  useEffect(() => {
    if (token) localStorage.setItem("token", token);
    if (role) localStorage.setItem("role", role);
  }, [token, role]);

  if (!token) return <Auth setToken={setToken} setRole={setRole} />;

  return (
    <Dashboard token={token} role={role} setToken={setToken} setRole={setRole} />
  );
}

/* -------------------- AUTH COMPONENT -------------------- */
function Auth({ setToken, setRole }) {
  const [u, setU] = useState("");
  const [p, setP] = useState("");
  const [r, setR] = useState("USER");

  const register = async () => {
    await axios.post(API + "/auth/register", {
      username: u,
      password: p,
      role: r,
    });
    alert("Registered!");
  };

  const login = async () => {
    const res = await axios.post(API + "/auth/login", {
      username: u,
      password: p,
    });

    if (res.data.error) {
      alert("Invalid login");
      return;
    }

    setToken(res.data.token);
    setRole(res.data.role);
  };

  return (
    <div className="card">
      <h2>Event Hub Login</h2>

      <input
        placeholder="Username"
        value={u}
        onChange={(e) => setU(e.target.value)}
      />

      <input
        placeholder="Password"
        type="password"
        value={p}
        onChange={(e) => setP(e.target.value)}
      />

      <select value={r} onChange={(e) => setR(e.target.value)}>
        <option>USER</option>
        <option>ADMIN</option>
      </select>

      <div className="row">
        <button onClick={login}>Login</button>
        <button onClick={register}>Register</button>
      </div>
    </div>
  );
}

/* -------------------- DASHBOARD COMPONENT -------------------- */
function Dashboard({ token, role, setToken, setRole }) {
  const headers = { Authorization: "Bearer " + token };
  const [events, setEvents] = useState([]);

  const [title, setTitle] = useState("");
  const [desc, setDesc] = useState("");
  const [category, setCategory] = useState("Tech");
  const [location, setLocation] = useState("");
  const [dateTime, setDateTime] = useState(""); // stored as YYYY-MM-DDTHH:mm
  const [seats, setSeats] = useState(0);

  useEffect(() => {
    load();
  }, []);

  const load = async () => {
    const r = await axios.get(API + "/events");
    setEvents(r.data);
  };

  const create = async () => {
    try {
      await axios.post(
        API + "/admin/events",
        { title, description: desc, category, location, dateTime, seats },
        { headers }
      );

      // reset fields
      setTitle("");
      setDesc("");
      setLocation("");
      setDateTime("");
      setSeats(0);

      load();
    } catch (e) {
      alert("Only admin can create");
    }
  };

  const del = async (id) => {
    try {
      await axios.delete(API + "/admin/events/" + id, { headers });
      load();
    } catch {
      alert("Only admin can delete");
    }
  };

  const reg = async (id) => {
    try {
      await axios.post(API + "/events/" + id + "/register", {}, { headers });
      alert("Registered!");
    } catch {
      alert("Event full or error");
    }
  };

  return (
    <div className="container">
      <div style={{ display: "flex", justifyContent: "space-between" }}>
        <h1>Event Hub</h1>

        <div>
          <b>{role}</b>
          <button
            onClick={() => {
              setToken("");
              setRole("");
              localStorage.removeItem("token");
              localStorage.removeItem("role");
            }}
          >
            Logout
          </button>
        </div>
      </div>

      {/* ---------------- CREATE EVENT (ADMIN ONLY) ---------------- */}
      {role === "ADMIN" && (
        <div className="card">
          <h3>Create Event</h3>

          <input
            placeholder="Title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />

          <input
            placeholder="Description"
            value={desc}
            onChange={(e) => setDesc(e.target.value)}
          />

          <input
            placeholder="Location"
            value={location}
            onChange={(e) => setLocation(e.target.value)}
          />

          {/* DATE PICKER */}
          <label>Date</label>
          <input
            type="date"
            value={dateTime.split("T")[0] || ""}
            onChange={(e) => {
              const date = e.target.value;
              const time = dateTime.split("T")[1] || "00:00";
              setDateTime(`${date}T${time}`);
            }}
          />

          {/* TIME PICKER */}
          <label>Time</label>
          <input
            type="time"
            value={dateTime.split("T")[1] || ""}
            onChange={(e) => {
              const time = e.target.value;
              const date =
                dateTime.split("T")[0] || dayjs().format("YYYY-MM-DD");
              setDateTime(`${date}T${time}`);
            }}
          />

          <input
            placeholder="Seats (0 = unlimited)"
            value={seats}
            onChange={(e) => setSeats(e.target.value)}
          />

          <select
            value={category}
            onChange={(e) => setCategory(e.target.value)}
          >
            <option>Tech</option>
            <option>Music</option>
            <option>Sports</option>
            <option>Workshop</option>
          </select>

          <button onClick={create}>Create Event</button>
        </div>
      )}

      {/* ---------------- EVENT LIST ---------------- */}
      <ul>
        {events.map((ev) => (
          <li key={ev.id}>
            <b>{ev.title}</b> [{ev.category}]<br />
            {ev.dateTime
              ? dayjs(ev.dateTime).format("YYYY-MM-DD HH:mm")
              : "No Date"}
            {" @ " + ev.location}
            <br />
            Seats: {ev.seats}
            <br />
            Created by: {ev.createdBy}
            <br />

            {role === "ADMIN" && (
              <button onClick={() => del(ev.id)}>Delete</button>
            )}

            <button onClick={() => reg(ev.id)}>Register</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
