package com.gym.repository;

import com.gym.model.Coach;
import com.gym.util.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoachRepository {
    private static final Logger logger = LoggerFactory.getLogger(CoachRepository.class);

    public void save(Coach coach) throws SQLException {
        String sql = "INSERT INTO coaches (name, email, phone, specialization) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, coach.getName());
            pstmt.setString(2, coach.getEmail());
            pstmt.setString(3, coach.getPhone());
            pstmt.setString(4, coach.getSpecialization());
            pstmt.executeUpdate();
            logger.info("Coach saved: {}", coach.getEmail());
        }
    }

    public void update(Coach coach) throws SQLException {
        String sql = "UPDATE coaches SET name = ?, phone = ?, specialization = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, coach.getName());
            pstmt.setString(2, coach.getPhone());
            pstmt.setString(3, coach.getSpecialization());
            pstmt.setInt(4, coach.getId());
            pstmt.executeUpdate();
            logger.info("Coach updated: {}", coach.getId());
        }
    }

    public Coach findById(int id) throws SQLException {
        String sql = "SELECT * FROM coaches WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToCoach(rs);
            }
        }
        return null;
    }

    public List<Coach> findAll() throws SQLException {
        List<Coach> coaches = new ArrayList<>();
        String sql = "SELECT * FROM coaches ORDER BY created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                coaches.add(mapRowToCoach(rs));
            }
        }
        return coaches;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM coaches WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            logger.info("Coach deleted: {}", id);
        }
    }

    private Coach mapRowToCoach(ResultSet rs) throws SQLException {
        Coach coach = new Coach();
        coach.setId(rs.getInt("id"));
        coach.setName(rs.getString("name"));
        coach.setEmail(rs.getString("email"));
        coach.setPhone(rs.getString("phone"));
        coach.setSpecialization(rs.getString("specialization"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            coach.setCreatedAt(createdAt.toLocalDateTime());
        }
        return coach;
    }
}