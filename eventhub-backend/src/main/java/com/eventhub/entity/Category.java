package com.eventhub.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 100)
    @Column(
        nullable = false,
        unique = true,
        length = 100
    )
    private String name;

    @Size(max = 500)
    @Column(length = 500)
    private String description;

    @OneToMany(
        mappedBy = "category"
    )
    private List<Event> events =
            new ArrayList<>();

    public Category() {
    }

    public Category(
            String name,
            String description) {

        this.name = name;
        this.description = description;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {

        this.description = description;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(
            List<Event> events) {

        this.events = events;
    }
}