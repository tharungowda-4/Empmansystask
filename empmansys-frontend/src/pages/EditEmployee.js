import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const EditEmployee = () => {
  const navigate = useNavigate();
  const userId = localStorage.getItem("userId");

  // Initialize state with empty values to match your Empmodel
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    gender: "",
    city: "",
    expectedSalary: "",
    knowsEnglish: false,
    phoneNumber: "",
    hscPercentage: "",
  });

  // 1. PRE-FILL LOGIC: Fetch data as soon as the page loads
  useEffect(() => {
    if (!userId) {
      navigate("/");
      return;
    }

    const loadExistingData = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/auth/profile/${userId}`,
        );
        setFormData(res.data); // This populates the form fields with existing data
      } catch (err) {
        console.error("Error fetching data for edit:", err);
        alert("Could not load your details. Redirecting to summary.");
        navigate("/summary");
      }
    };

    loadExistingData();
  }, [userId, navigate]);

  // 2. UPDATE LOGIC: Handle the PUT request
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(
        `http://localhost:8080/api/auth/profile/${userId}`,
        formData,
      );
      alert("Details updated successfully!");
      navigate("/summary"); // Requirement: Redirect to summary after saving
    } catch (err) {
      alert("Update failed. Please check your network or server.");
    }
  };

  return (
    <div className="row justify-content-center">
      <div className="col-md-6">
        <div className="card shadow p-4 border-0 mb-5">
          <h2 className="text-center mb-4 text-warning fw-bold">
            Edit Employee Details
          </h2>
          <form onSubmit={handleSubmit}>
            {/* Full Name - Type: text */}
            <div className="mb-3">
              <label className="form-label fw-bold">Full Name</label>
              <input
                type="text"
                className="form-control"
                value={formData.name}
                required
                onChange={(e) =>
                  setFormData({ ...formData, name: e.target.value })
                }
              />
            </div>

            {/* Email - Type: email */}
            <div className="mb-3">
              <label className="form-label fw-bold">Email</label>
              <input
                type="email"
                className="form-control"
                value={formData.email}
                required
                onChange={(e) =>
                  setFormData({ ...formData, email: e.target.value })
                }
              />
            </div>

            {/* Gender - Dropdown Requirement */}
            <div className="mb-3">
              <label className="form-label fw-bold">Gender</label>
              <select
                className="form-select"
                value={formData.gender}
                required
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

            {/* City - Type: text */}
            <div className="mb-3">
              <label className="form-label fw-bold">City</label>
              <input
                type="text"
                className="form-control"
                value={formData.city}
                required
                onChange={(e) =>
                  setFormData({ ...formData, city: e.target.value })
                }
              />
            </div>

            {/* Expected Salary - Type: number (Double) */}
            <div className="mb-3">
              <label className="form-label fw-bold">Expected Salary</label>
              <input
                type="number"
                step="0.01"
                className="form-control"
                value={formData.expectedSalary}
                required
                onChange={(e) =>
                  setFormData({ ...formData, expectedSalary: e.target.value })
                }
              />
            </div>

            {/* HSC Percentage - Type: number (Float) */}
            <div className="mb-3">
              <label className="form-label fw-bold">12th Percentage (%)</label>
              <input
                type="number"
                step="0.01"
                className="form-control"
                value={formData.hscPercentage}
                required
                onChange={(e) =>
                  setFormData({ ...formData, hscPercentage: e.target.value })
                }
              />
            </div>

            {/* English Proficiency - Checkbox */}
            <div className="form-check mb-4">
              <input
                className="form-check-input"
                type="checkbox"
                id="editEnglish"
                checked={formData.knowsEnglish}
                onChange={(e) =>
                  setFormData({ ...formData, knowsEnglish: e.target.checked })
                }
              />
              <label className="form-check-label" htmlFor="editEnglish">
                I know English (Mandatory)
              </label>
            </div>

            {/* Action Buttons */}
            <div className="d-grid gap-2">
              <button
                type="submit"
                className="btn btn-success fw-bold py-2 shadow-sm"
              >
                SAVE CHANGES (UPDATE)
              </button>
              <button
                type="button"
                className="btn btn-secondary py-2 shadow-sm"
                onClick={() => navigate("/summary")}
              >
                CANCEL
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default EditEmployee;
