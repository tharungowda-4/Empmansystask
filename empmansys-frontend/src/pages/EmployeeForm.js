import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const EmployeeForm = () => {
  const navigate = useNavigate();
  const userId = localStorage.getItem("userId");
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    age: "",
    city: "",
    expectedSalary: "",
    knowsEnglish: false,
    phoneNumber: "",
    hscPercentage: "",
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Validation logic here...
    try {
      await axios.post(
        `http://localhost:8080/api/auth/profile/${userId}`,
        formData,
      );
      navigate("/summary");
    } catch (err) {
      alert("Error saving data");
    }
  };

  return (
    <div className="row justify-content-center">
      <div className="col-md-6">
        {" "}
        {/* Centers the form and limits width */}
        <div className="card shadow p-4">
          <h2 className="text-center mb-4">Employee Registration Form</h2>
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label className="form-label fw-bold">Full Name</label>
              <input
                type="text"
                className="form-control"
                required
                onChange={(e) =>
                  setFormData({ ...formData, name: e.target.value })
                }
              />
            </div>
            <div className="mb-3">
              <label className="form-label fw-bold">Email</label>
              <input
                type="email"
                className="form-control"
                required
                onChange={(e) =>
                  setFormData({ ...formData, email: e.target.value })
                }
              />
            </div>
            <div className="mb-3">
              <label className="form-label fw-bold">Gender</label>
              <select
                className="form-select"
                required
                value={formData.gender}
                onChange={(e) =>
                  setFormData({ ...formData, gender: e.target.value })
                }
              >
                <option value="">Select Gender</option>
                <option value="MALE">MALE</option>
                <option value="FEMALE">FEMALE</option>
                <option value="OTHERS">OTHERS</option>
              </select>
            </div>
           
            <div className="mb-3">
              <label className="form-label fw-bold">12th Percentage</label>
              <input
                type="number"
                step="0.01"
                className="form-control"
                required
                onChange={(e) =>
                  setFormData({ ...formData, hscPercentage: e.target.value })
                }
              />
            </div>
            <div className="mb-3">
              <label className="form-label fw-bold">City</label>
              <input
                type="text"
                className="form-control"
                required
                onChange={(e) =>
                  setFormData({ ...formData, city: e.target.value })
                }
              />
            </div>
            <div className="mb-3">
              <label className="form-label fw-bold">Expected Salary</label>
              <input
                type="number"
                className="form-control"
                required
                onChange={(e) =>
                  setFormData({ ...formData, expectedSalary: e.target.value })
                }
              />
            </div>
            <div className="form-check mb-4">
              <input
                className="form-check-input"
                type="checkbox"
                id="english"
                onChange={(e) =>
                  setFormData({ ...formData, knowsEnglish: e.target.checked })
                }
              />
              <label className="form-check-label" htmlFor="english">
                I definitely know English
              </label>
            </div>
            <button
              type="submit"
              className="btn btn-primary w-100 py-2 fw-bold"
            >
              Register Profile
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default EmployeeForm;
