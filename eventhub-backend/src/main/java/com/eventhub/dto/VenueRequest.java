package com.eventhub.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class VenueRequest {

    @NotBlank(message = "Venue name is required")
    @Size(
        min = 2,
        max = 150,
        message = "Venue name must be between 2 and 150 characters"
    )
    private String name;

    @NotBlank(message = "Address is required")
    @Size(
        max = 250,
        message = "Address cannot exceed 250 characters"
    )
    private String address;

    @NotBlank(message = "City is required")
    @Size(
        max = 100,
        message = "City cannot exceed 100 characters"
    )
    private String city;

    @NotBlank(message = "State is required")
    @Size(
        max = 100,
        message = "State cannot exceed 100 characters"
    )
    private String state;

    @NotBlank(message = "Pincode is required")
    @Pattern(
        regexp = "^[1-9][0-9]{5}$",
        message = "Pincode must be a valid 6-digit number"
    )
    private String pincode;

    @NotBlank(message = "Country is required")
    @Size(
        max = 100,
        message = "Country cannot exceed 100 characters"
    )
    private String country;

    @Min(
        value = 1,
        message = "Capacity must be at least 1"
    )
    private Integer capacity;

    // Default Constructor
    public VenueRequest() {
    }

    // Parameterized Constructor
    public VenueRequest(
            String name,
            String address,
            String city,
            String state,
            String pincode,
            String country,
            Integer capacity) {

        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.country = country;
        this.capacity = capacity;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}