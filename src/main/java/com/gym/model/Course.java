package com.gym.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Course implements Serializable {
    private int id;
    private String name;
    private int coachId;
    private String coachName;
    private String category;
    private LocalDate scheduleDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int maxCapacity;
    private int currentEnrollment;
    private LocalDateTime createdAt;

    public Course() {}

    public Course(String name, int coachId, String category, LocalDate scheduleDate, 
                  LocalTime startTime, LocalTime endTime, int maxCapacity) {
        this.name = name;
        this.coachId = coachId;
        this.category = category;
        this.scheduleDate = scheduleDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxCapacity = maxCapacity;
        this.currentEnrollment = 0;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCoachId() { return coachId; }
    public void setCoachId(int coachId) { this.coachId = coachId; }
    public String getCoachName() { return coachName; }
    public void setCoachName(String coachName) { this.coachName = coachName; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public LocalDate getScheduleDate() { return scheduleDate; }
    public void setScheduleDate(LocalDate scheduleDate) { this.scheduleDate = scheduleDate; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    public int getCurrentEnrollment() { return currentEnrollment; }
    public void setCurrentEnrollment(int currentEnrollment) { this.currentEnrollment = currentEnrollment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", name='" + name + "\''+ '}';
    }
}