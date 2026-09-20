package com.eventhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddressRequest {

    @NotBlank(message = "Address is required")
    @Size(
        max = 255,
        message = "Address cannot exceed 255 characters"
    )
    private String addressLine;

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

    // Default Constructor
    public AddressRequest() {
    }

    // Parameterized Constructor
    public AddressRequest(
            String addressLine,
            String city,
            String state,
            String pincode) {

        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    // Getters and Setters

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
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
}