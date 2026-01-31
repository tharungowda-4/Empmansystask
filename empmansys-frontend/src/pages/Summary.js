import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Summary = () => {
  const [emp, setEmp] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  // CORRECTED LINE:
  const userId = localStorage.getItem("userId")
    ? parseInt(localStorage.getItem("userId"))
    : null;

  useEffect(() => {
    const fetchProfile = async () => {
      const rawId = localStorage.getItem("userId");
      if (!rawId) {
        navigate("/");
        return;
      }

      const id = parseInt(rawId);
      try {
        // The URL remains the same, but the DB will now find the record under user_id
        const res = await axios.get(
          `http://localhost:8080/api/auth/profile/${id}`,
        );
        if (res.data) {
          setEmp(res.data);
        }
      } catch (err) {
        console.error(
          "Profile fetch failed. User might not have a profile yet.",
        );
        setEmp(null);
      } finally {
        setLoading(false);
      }
    };

    fetchProfile();
  }, [navigate]); // navigate is stable, so this runs once on mount

  const handleDelete = async () => {
    if (window.confirm("Are you sure you want to delete your profile?")) {
      try {
        await axios.delete(`http://localhost:8080/api/auth/profile/${userId}`);
        setEmp(null); // This will trigger the "Add Employee" button view
      } catch (err) {
        alert("Delete failed");
      }
    }
  };

  if (loading)
    return (
      <div className="text-center mt-5">
        <h4>Checking Records...</h4>
      </div>
    );

  return (
    <div className="container mt-4">
      {!emp ? (
        <div className="text-center p-5 card shadow border-0">
          <h3 className="mb-3">Welcome!</h3>
          <p className="text-muted">
            You haven't registered your employee details yet.
          </p>
          <button
            className="btn btn-primary btn-lg"
            onClick={() => navigate("/register-details")}
          >
            + Add Employee Details
          </button>
        </div>
      ) : (
        <div className="row justify-content-center">
          <div className="col-md-6 card shadow p-4 border-0">
            <h3 className="text-center mb-4 text-primary border-bottom pb-2">
              Employee Summary
            </h3>
            <div className="lh-lg">
              <p>
                <strong>Name:</strong> {emp.name}
              </p>
              <p>
                <strong>Email:</strong> {emp.email}
              </p>
              <p>
                <strong>Gender:</strong> {emp.gender}
              </p>
              <p>
                <strong>City:</strong> {emp.city}
              </p>
              <p>
                <strong>Expected Salary:</strong> ₹{emp.expectedSalary}
              </p>
              <p>
                <strong>12th %:</strong> {emp.hscPercentage}%
              </p>
            </div>
            <div className="d-flex justify-content-between mt-4">
              <button
                className="btn btn-warning fw-bold px-4"
                onClick={() => navigate("/edit-employee")}
              >
                EDIT
              </button>
              <button
                className="btn btn-danger fw-bold px-4"
                onClick={handleDelete}
              >
                DELETE
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Summary;
