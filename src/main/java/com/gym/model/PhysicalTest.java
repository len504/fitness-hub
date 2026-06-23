package com.gym.model;

import java.time.LocalDate;

/**
 * PhysicalTest Entity Class
 * Represents physical test data for a member
 */
public class PhysicalTest {
    private Integer id;
    private Integer memberId;
    private String memberName;
    private LocalDate testDate;
    private Double weight;
    private Double height;
    private Double bodyFatPercentage;
    private Double muscleMass;
    private Double bmi;
    private Integer restingHeartRate;
    private String notes;

    public PhysicalTest() {
    }

    public PhysicalTest(Integer memberId, LocalDate testDate, Double weight, Double height, 
                        Double bodyFatPercentage, Double muscleMass, Integer restingHeartRate) {
        this.memberId = memberId;
        this.testDate = testDate;
        this.weight = weight;
        this.height = height;
        this.bodyFatPercentage = bodyFatPercentage;
        this.muscleMass = muscleMass;
        this.restingHeartRate = restingHeartRate;
        this.bmi = calculateBMI(weight, height);
    }

    /**
     * Calculate BMI from weight and height
     */
    private static Double calculateBMI(Double weight, Double height) {
        if (height == null || height == 0) return null;
        return weight / (height * height);
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getBodyFatPercentage() {
        return bodyFatPercentage;
    }

    public void setBodyFatPercentage(Double bodyFatPercentage) {
        this.bodyFatPercentage = bodyFatPercentage;
    }

    public Double getMuscleMass() {
        return muscleMass;
    }

    public void setMuscleMass(Double muscleMass) {
        this.muscleMass = muscleMass;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public Integer getRestingHeartRate() {
        return restingHeartRate;
    }

    public void setRestingHeartRate(Integer restingHeartRate) {
        this.restingHeartRate = restingHeartRate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "PhysicalTest{" +
                "id=" + id +
                ", memberName='" + memberName + '\'' +
                ", testDate=" + testDate +
                ", weight=" + weight +
                ", bmi=" + bmi +
                '}';
    }
}
