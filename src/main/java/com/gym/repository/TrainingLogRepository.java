package com.gym.repository;

import com.gym.model.TrainingLog;
import com.gym.util.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainingLogRepository {
    private static final Logger logger = LoggerFactory.getLogger(TrainingLogRepository.class);

    public void save(TrainingLog log) throws SQLException {
        String sql = "INSERT INTO training_logs (member_id, log_date, exercise_name, sets_completed, reps_completed, weight_used, duration_minutes, calories_burned, feelings) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, log.getMemberId());
            pstmt.setDate(2, Date.valueOf(log.getLogDate()));
            pstmt.setString(3, log.getExerciseName());
            pstmt.setInt(4, log.getSetsCompleted());
            pstmt.setInt(5, log.getRepsCompleted());
            pstmt.setBigDecimal(6, log.getWeightUsed());
            pstmt.setInt(7, log.getDurationMinutes());
            pstmt.setBigDecimal(8, log.getCaloriesBurned());
            pstmt.setString(9, log.getFeelings());
            pstmt.executeUpdate();
            logger.info("Training log saved for member: {}", log.getMemberId());
        }
    }

    public TrainingLog findById(int id) throws SQLException {
        String sql = "SELECT * FROM training_logs WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToLog(rs);
            }
        }
        return null;
    }

    public List<TrainingLog> findByMemberId(int memberId) throws SQLException {
        List<TrainingLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM training_logs WHERE member_id = ? ORDER BY log_date DESC";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                logs.add(mapRowToLog(rs));
            }
        }
        return logs;
    }

    public List<TrainingLog> findByMemberIdAndDateRange(int memberId, LocalDate startDate, LocalDate endDate) throws SQLException {
        List<TrainingLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM training_logs WHERE member_id = ? AND log_date BETWEEN ? AND ? ORDER BY log_date DESC";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            pstmt.setDate(2, Date.valueOf(startDate));
            pstmt.setDate(3, Date.valueOf(endDate));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                logs.add(mapRowToLog(rs));
            }
        }
        return logs;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM training_logs WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            logger.info("Training log deleted: {}", id);
        }
    }

    private TrainingLog mapRowToLog(ResultSet rs) throws SQLException {
        TrainingLog log = new TrainingLog();
        log.setId(rs.getInt("id"));
        log.setMemberId(rs.getInt("member_id"));
        Date logDate = rs.getDate("log_date");
        if (logDate != null) {
            log.setLogDate(logDate.toLocalDate());
        }
        log.setExerciseName(rs.getString("exercise_name"));
        log.setSetsCompleted(rs.getInt("sets_completed"));
        log.setRepsCompleted(rs.getInt("reps_completed"));
        log.setWeightUsed(rs.getBigDecimal("weight_used"));
        log.setDurationMinutes(rs.getInt("duration_minutes"));
        log.setCaloriesBurned(rs.getBigDecimal("calories_burned"));
        log.setFeelings(rs.getString("feelings"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            log.setCreatedAt(createdAt.toLocalDateTime());
        }
        return log;
    }
}