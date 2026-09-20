
import axios from "axios";

const API_URL =
  "http://localhost:8080/api/auth";

// ==============================
// LOGIN
// ==============================

export const loginUser = async (loginData) => {

  try {

    const response =
      await axios.post(
        `${API_URL}/login`,
        loginData
      );

    return response.data;

  } catch (error) {

    console.error(
      "Login error:",
      error
    );

    throw error;
  }
};

// ==============================
// REGISTER
// ==============================

export const registerUser = async (userData) => {

  try {

    const response =
      await axios.post(
        `${API_URL}/register`,
        userData
      );

    return response.data;

  } catch (error) {

    console.error(
      "Registration error:",
      error
    );

    throw error;
  }
};

// ==============================
// LOGOUT
// ==============================

export const logoutUser = () => {

  localStorage.removeItem("token");
  localStorage.removeItem("user");
  localStorage.removeItem("role");
  localStorage.removeItem("name");
  localStorage.removeItem("email");
  localStorage.removeItem("userId");
};

// ==============================
// SAVE USER
// ==============================

export const saveUser = (user) => {

  localStorage.setItem(
    "user",
    JSON.stringify(user)
  );
};

// ==============================
// GET USER
// ==============================

export const getUser = () => {

  const user =
    localStorage.getItem("user");

  if (user) {

    return JSON.parse(user);

  }

  return null;
};

// ==============================
// SAVE TOKEN
// ==============================

export const saveToken = (token) => {

  localStorage.setItem(
    "token",
    token
  );
};

// ==============================
// GET TOKEN
// ==============================

export const getToken = () => {

  return localStorage.getItem("token");
};

// ==============================
// CHECK LOGIN
// ==============================

export const isLoggedIn = () => {

  return !!localStorage.getItem("token");
};

