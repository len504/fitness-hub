package com.gym.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrainingPlan implements Serializable {
    private int id;
    private int memberId;
    private int coachId;
    private String planName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private List<TrainingExercise> exercises;

    public TrainingPlan() {
        this.exercises = new ArrayList<>();
    }

    public TrainingPlan(int memberId, int coachId, String planName, LocalDate startDate, LocalDate endDate) {
        this.memberId = memberId;
        this.coachId = coachId;
        this.planName = planName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.exercises = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }
    public int getCoachId() { return coachId; }
    public void setCoachId(int coachId) { this.coachId = coachId; }
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<TrainingExercise> getExercises() { return exercises; }
    public void setExercises(List<TrainingExercise> exercises) { this.exercises = exercises; }
    public void addExercise(TrainingExercise exercise) { this.exercises.add(exercise); }

    @Override
    public String toString() {
        return "TrainingPlan{" + "id=" + id + ", planName='" + planName + "\''+ '}';
    }
}