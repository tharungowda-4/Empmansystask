import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Auth from "./pages/Auth";
import EmployeeForm from "./pages/EmployeeForm";
import Summary from "./pages/Summary";
import EditEmployee from "./pages/EditEmployee";
import "bootstrap/dist/css/bootstrap.min.css";

function App() {
  return (
    <Router>
      <Navbar />
      <div className="container mt-4">
        <Routes>
          <Route path="/" element={<Auth />} />
          <Route path="/register-details" element={<EmployeeForm />} />
          <Route path="/summary" element={<Summary />} />
          <Route path="/edit-employee" element={<EditEmployee />} />
        </Routes>
      </div>
    </Router>
  );
}
export default App;
