package com.eventhub.dto;

public class VenueResponse {

    private Long id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String country;
    private Integer capacity;

    // Default Constructor
    public VenueResponse() {
    }

    // Parameterized Constructor
    public VenueResponse(
            Long id,
            String name,
            String address,
            String city,
            String state,
            String pincode,
            String country,
            Integer capacity) {

        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.country = country;
        this.capacity = capacity;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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