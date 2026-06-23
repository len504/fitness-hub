package com.gym.repository;

import com.gym.model.TrainingExercise;
import com.gym.util.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainingExerciseRepository {
    private static final Logger logger = LoggerFactory.getLogger(TrainingExerciseRepository.class);

    public void save(TrainingExercise exercise) throws SQLException {
        String sql = "INSERT INTO training_exercises (plan_id, exercise_name, sets, repetitions, weight, rest_interval, notes) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, exercise.getPlanId());
            pstmt.setString(2, exercise.getExerciseName());
            pstmt.setInt(3, exercise.getSets());
            pstmt.setInt(4, exercise.getRepetitions());
            pstmt.setBigDecimal(5, exercise.getWeight());
            pstmt.setInt(6, exercise.getRestInterval());
            pstmt.setString(7, exercise.getNotes());
            pstmt.executeUpdate();
            logger.info("Exercise saved: {}", exercise.getExerciseName());
        }
    }

    public void update(TrainingExercise exercise) throws SQLException {
        String sql = "UPDATE training_exercises SET exercise_name = ?, sets = ?, repetitions = ?, weight = ?, rest_interval = ?, notes = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, exercise.getExerciseName());
            pstmt.setInt(2, exercise.getSets());
            pstmt.setInt(3, exercise.getRepetitions());
            pstmt.setBigDecimal(4, exercise.getWeight());
            pstmt.setInt(5, exercise.getRestInterval());
            pstmt.setString(6, exercise.getNotes());
            pstmt.setInt(7, exercise.getId());
            pstmt.executeUpdate();
            logger.info("Exercise updated: {}", exercise.getId());
        }
    }

    public TrainingExercise findById(int id) throws SQLException {
        String sql = "SELECT * FROM training_exercises WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToExercise(rs);
            }
        }
        return null;
    }

    public List<TrainingExercise> findByPlanId(int planId) throws SQLException {
        List<TrainingExercise> exercises = new ArrayList<>();
        String sql = "SELECT * FROM training_exercises WHERE plan_id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, planId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                exercises.add(mapRowToExercise(rs));
            }
        }
        return exercises;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM training_exercises WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            logger.info("Exercise deleted: {}", id);
        }
    }

    private TrainingExercise mapRowToExercise(ResultSet rs) throws SQLException {
        TrainingExercise exercise = new TrainingExercise();
        exercise.setId(rs.getInt("id"));
        exercise.setPlanId(rs.getInt("plan_id"));
        exercise.setExerciseName(rs.getString("exercise_name"));
        exercise.setSets(rs.getInt("sets"));
        exercise.setRepetitions(rs.getInt("repetitions"));
        exercise.setWeight(rs.getBigDecimal("weight"));
        exercise.setRestInterval(rs.getInt("rest_interval"));
        exercise.setNotes(rs.getString("notes"));
        return exercise;
    }
}