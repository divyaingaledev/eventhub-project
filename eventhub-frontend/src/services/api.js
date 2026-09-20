import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
});

// ==========================================
// REQUEST INTERCEPTOR
// ==========================================

api.interceptors.request.use(
  (config) => {

    const token = localStorage.getItem("token");

    console.log("========== API REQUEST ==========");
    console.log("URL:", config.url);
    console.log("Method:", config.method);
    console.log(
      "Token exists:",
      token ? "YES" : "NO"
    );

    if (token) {

      config.headers = config.headers || {};

      config.headers.Authorization =
        `Bearer ${token}`;

      console.log(
        "Authorization:",
        "Bearer [TOKEN]"
      );
    } else {

      console.log(
        "Authorization: NOT SENT"
      );
    }

    console.log(
      "================================"
    );

    return config;
  },
  (error) => {

    return Promise.reject(error);
  }
);

// ==========================================
// RESPONSE INTERCEPTOR
// ==========================================

api.interceptors.response.use(
  (response) => {

    return response;
  },

  (error) => {

    if (error.response?.status === 401) {

      console.error(
        "401 Unauthorized - JWT missing, invalid or expired"
      );
    }

    if (error.response?.status === 403) {

      console.error(
        "403 Forbidden - Spring Security rejected request"
      );
    }

    return Promise.reject(error);
  }
);

export default api;