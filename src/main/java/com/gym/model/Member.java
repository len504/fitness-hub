package com.gym.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Member implements Serializable {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String gender;
    private LocalDate birthDate;
    private String fitnessGoal;
    private String avatarPath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Member() {
    }

    public Member(String name, String email, String phone, String gender, LocalDate birthDate, String fitnessGoal) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.birthDate = birthDate;
        this.fitnessGoal = fitnessGoal;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public String getFitnessGoal() { return fitnessGoal; }
    public void setFitnessGoal(String fitnessGoal) { this.fitnessGoal = fitnessGoal; }
    public String getAvatarPath() { return avatarPath; }
    public void setAvatarPath(String avatarPath) { this.avatarPath = avatarPath; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Member{" + "id=" + id + ", name='" + name + "\''+ ", email='" + email + "\''+ '}';
    }
}