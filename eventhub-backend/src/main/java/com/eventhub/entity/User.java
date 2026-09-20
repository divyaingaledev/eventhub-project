package com.eventhub.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.eventhub.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "full_name",
        nullable = false,
        length = 100
    )
    private String fullName;

    @Column(
        nullable = false,
        unique = true,
        length = 150
    )
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 15)
    private String mobile;

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        length = 20
    )
    private Role role = Role.USER;

    @Column(
        name = "created_at",
        nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
        name = "updated_at",
        nullable = false
    )
    private LocalDateTime updatedAt;

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Address> addresses =
            new ArrayList<>();

    @OneToMany(
        mappedBy = "user"
    )
    private List<Booking> bookings =
            new ArrayList<>();

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public User() {
    }

    public User(
            String fullName,
            String email,
            String password,
            String mobile,
            Role role) {

        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.mobile = mobile;

        this.role =
                role != null
                ? role
                : Role.USER;
    }

    // ==========================================
    // PRE PERSIST
    // ==========================================

    @PrePersist
    protected void onCreate() {

        LocalDateTime now =
                LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;

        if (this.role == null) {
            this.role = Role.USER;
        }
    }

    // ==========================================
    // PRE UPDATE
    // ==========================================

    @PreUpdate
    protected void onUpdate() {

        this.updatedAt =
                LocalDateTime.now();
    }

    // ==========================================
    // GETTERS & SETTERS
    // ==========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role =
                role != null
                ? role
                : Role.USER;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}