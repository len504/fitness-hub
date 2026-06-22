package com.gym.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TrainingExercise implements Serializable {
    private int id;
    private int planId;
    private String exerciseName;
    private int sets;
    private int repetitions;
    private BigDecimal weight;
    private int restInterval;
    private String notes;

    public TrainingExercise() {}

    public TrainingExercise(String exerciseName, int sets, int repetitions, BigDecimal weight, int restInterval) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.repetitions = repetitions;
        this.weight = weight;
        this.restInterval = restInterval;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPlanId() { return planId; }
    public void setPlanId(int planId) { this.planId = planId; }
    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }
    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }
    public int getRepetitions() { return repetitions; }
    public void setRepetitions(int repetitions) { this.repetitions = repetitions; }
    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }
    public int getRestInterval() { return restInterval; }
    public void setRestInterval(int restInterval) { this.restInterval = restInterval; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return "TrainingExercise{" + "id=" + id + ", exerciseName='" + exerciseName + "\''+ '}';
    }
}