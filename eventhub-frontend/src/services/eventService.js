
import api from "./api";

const API_URL = "/events";


// GET ALL EVENTS


export const getAllEvents = async () => {

  try {

    const response = await api.get(API_URL);

    return response.data;

  } catch (error) {

    console.error(
      "Error fetching events:",
      error
    );

    throw error;
  }
};


// GET EVENT BY ID


export const getEventById = async (id) => {

  try {

    const response =
      await api.get(`${API_URL}/${id}`);

    return response.data;

  } catch (error) {

    console.error(
      "Error fetching event:",
      error
    );

    throw error;
  }
};

// ADD NEW EVENT


export const createEvent = async (eventData) => {

  try {

    const response =
      await api.post(API_URL, eventData);

    return response.data;

  } catch (error) {

    console.error(
      "Error creating event:",
      error
    );

    throw error;
  }
};


// UPDATE EVENT


export const updateEvent = async (id, eventData) => {

  try {

    const response =
      await api.put(
        `${API_URL}/${id}`,
        eventData
      );

    return response.data;

  } catch (error) {

    console.error(
      "Error updating event:",
      error
    );

    throw error;
  }
};

// ==============================
// DELETE EVENT
// ==============================

export const deleteEvent = async (id) => {

  try {

    const response =
      await api.delete(
        `${API_URL}/${id}`
      );

    return response.data;

  } catch (error) {

    console.error(
      "Error deleting event:",
      error
    );

    throw error;
  }
};

