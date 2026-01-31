import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Auth = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({ phoneNumber: "", password: "" });
  const navigate = useNavigate();

  const handleAuth = async (e) => {
    e.preventDefault();
    if (formData.phoneNumber.length !== 10) {
      alert("Phone number must be 10 digits.");
      return;
    }

    try {
      const endpoint = isLogin ? "login" : "signup";
      const response = await axios.post(
        `http://localhost:8080/api/auth/${endpoint}`,
        formData,
      );
      if (isLogin) {
        localStorage.setItem("userId", response.data.id);
        navigate("/summary"); // Requirement: Redirect to summary
      } else {
        alert("Signup Successful! Please Login.");
        setIsLogin(true);
      }
    } catch (err) {
      alert("Error: " + (err.response?.data || "Server unreachable"));
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-5">
          <div className="card shadow-lg border-0 rounded-3">
            <div className="card-body p-5">
              <h2 className="text-center fw-bold mb-4">
                {isLogin ? "Welcome Back" : "Create Account"}
              </h2>
              <form onSubmit={handleAuth}>
                <div className="mb-3">
                  <label className="form-label fw-semibold">Phone Number</label>
                  <input
                    type="number"
                    className="form-control form-control-lg"
                    placeholder="Enter your phoneNumber"
                    required
                    onChange={(e) =>
                      setFormData({ ...formData, phoneNumber: e.target.value })
                    }
                  />
                </div>
                <div className="mb-4">
                  <label className="form-label fw-semibold">Password</label>
                  <input
                    type="password"
                    className="form-control form-control-lg"
                    placeholder="Enter your Password"
                    required
                    onChange={(e) =>
                      setFormData({ ...formData, password: e.target.value })
                    }
                  />
                </div>
                <button
                  type="submit"
                  className="btn btn-primary btn-lg w-100 shadow-sm"
                >
                  {isLogin ? "Login" : "Sign Up"}
                </button>
              </form>
              <hr className="my-4" />
              <div className="text-center">
                <button
                  className="btn btn-link text-decoration-none"
                  onClick={() => setIsLogin(!isLogin)}
                >
                  {isLogin
                    ? "New here? Create an account"
                    : "Already registered? Login here"}
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Auth;
