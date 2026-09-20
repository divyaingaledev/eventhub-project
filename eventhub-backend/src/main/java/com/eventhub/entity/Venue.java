package com.eventhub.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "venues")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Venue name is required")
    @Size(
        min = 2,
        max = 150,
        message = "Venue name must be between 2 and 150 characters"
    )
    @Column(
        nullable = false,
        length = 150
    )
    private String name;

    @NotBlank(message = "Address is required")
    @Size(
        max = 250,
        message = "Address cannot exceed 250 characters"
    )
    @Column(
        nullable = false,
        length = 250
    )
    private String address;

    @NotBlank(message = "City is required")
    @Size(
        max = 100,
        message = "City cannot exceed 100 characters"
    )
    @Column(
        nullable = false,
        length = 100
    )
    private String city;

    @NotBlank(message = "State is required")
    @Size(
        max = 100,
        message = "State cannot exceed 100 characters"
    )
    @Column(
        nullable = false,
        length = 100
    )
    private String state;

    @NotBlank(message = "Pincode is required")
    @Size(
        min = 6,
        max = 10,
        message = "Pincode must be between 6 and 10 characters"
    )
    @Column(
        nullable = false,
        length = 10
    )
    private String pincode;

    @NotBlank(message = "Country is required")
    @Size(
        max = 100,
        message = "Country cannot exceed 100 characters"
    )
    @Column(
        nullable = false,
        length = 100
    )
    private String country;

    @Min(
        value = 1,
        message = "Capacity must be at least 1"
    )
    @Column(
        nullable = false
    )
    private Integer capacity;

    @OneToMany(
        mappedBy = "venue",
        cascade = CascadeType.ALL
    )
    private List<Event> events = new ArrayList<>();

    // =========================
    // DEFAULT CONSTRUCTOR
    // =========================

    public Venue() {
    }

    // =========================
    // PARAMETERIZED CONSTRUCTOR
    // =========================

    public Venue(
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

    // =========================
    // GETTERS & SETTERS
    // =========================

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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}