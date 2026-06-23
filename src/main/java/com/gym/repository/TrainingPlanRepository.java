package com.gym.repository;

import com.gym.model.TrainingPlan;
import com.gym.util.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainingPlanRepository {
    private static final Logger logger = LoggerFactory.getLogger(TrainingPlanRepository.class);

    public void save(TrainingPlan plan) throws SQLException {
        String sql = "INSERT INTO training_plans (member_id, coach_id, plan_name, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, plan.getMemberId());
            pstmt.setInt(2, plan.getCoachId());
            pstmt.setString(3, plan.getPlanName());
            pstmt.setDate(4, Date.valueOf(plan.getStartDate()));
            pstmt.setDate(5, plan.getEndDate() != null ? Date.valueOf(plan.getEndDate()) : null);
            pstmt.executeUpdate();
            logger.info("Training plan saved: {}", plan.getPlanName());
        }
    }

    public void update(TrainingPlan plan) throws SQLException {
        String sql = "UPDATE training_plans SET plan_name = ?, start_date = ?, end_date = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plan.getPlanName());
            pstmt.setDate(2, Date.valueOf(plan.getStartDate()));
            pstmt.setDate(3, plan.getEndDate() != null ? Date.valueOf(plan.getEndDate()) : null);
            pstmt.setInt(4, plan.getId());
            pstmt.executeUpdate();
            logger.info("Training plan updated: {}", plan.getId());
        }
    }

    public TrainingPlan findById(int id) throws SQLException {
        String sql = "SELECT * FROM training_plans WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToPlan(rs);
            }
        }
        return null;
    }

    public List<TrainingPlan> findByMemberId(int memberId) throws SQLException {
        List<TrainingPlan> plans = new ArrayList<>();
        String sql = "SELECT * FROM training_plans WHERE member_id = ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                plans.add(mapRowToPlan(rs));
            }
        }
        return plans;
    }

    public List<TrainingPlan> findAll() throws SQLException {
        List<TrainingPlan> plans = new ArrayList<>();
        String sql = "SELECT * FROM training_plans ORDER BY created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                plans.add(mapRowToPlan(rs));
            }
        }
        return plans;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM training_plans WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            logger.info("Training plan deleted: {}", id);
        }
    }

    private TrainingPlan mapRowToPlan(ResultSet rs) throws SQLException {
        TrainingPlan plan = new TrainingPlan();
        plan.setId(rs.getInt("id"));
        plan.setMemberId(rs.getInt("member_id"));
        plan.setCoachId(rs.getInt("coach_id"));
        plan.setPlanName(rs.getString("plan_name"));
        Date startDate = rs.getDate("start_date");
        if (startDate != null) {
            plan.setStartDate(startDate.toLocalDate());
        }
        Date endDate = rs.getDate("end_date");
        if (endDate != null) {
            plan.setEndDate(endDate.toLocalDate());
        }
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            plan.setCreatedAt(createdAt.toLocalDateTime());
        }
        return plan;
    }
}