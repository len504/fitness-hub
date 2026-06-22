package com.gym.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Coach implements Serializable {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String specialization;
    private LocalDateTime createdAt;

    public Coach() {}

    public Coach(String name, String email, String phone, String specialization) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Coach{" + "id=" + id + ", name='" + name + "\''+ '}';
    }
}