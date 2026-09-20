package com.eventhub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "addresses")
public class Address {

    // PRIMARY KEY
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    // ADDRESS DETAILS


    @NotBlank(message = "Street is required")
    @Size(max = 200, message = "Street cannot exceed 200 characters")
    @Column(nullable = false, length = 200)
    private String street;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 100, message = "State cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String state;

    @NotBlank(message = "Pincode is required")
    @Size(min = 6, max = 10, message = "Invalid pincode")
    @Column(nullable = false, length = 10)
    private String pincode;

    @NotBlank(message = "Country is required")
    @Column(nullable = false, length = 100)
    private String country;

    // RELATIONSHIP WITH USER
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

   
    // CONSTRUCTORS


    public Address() {
    }

    public Address(
            String street,
            String city,
            String state,
            String pincode,
            String country,
            User user) {

        this.street = street;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.country = country;
        this.user = user;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}