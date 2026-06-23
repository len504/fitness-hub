package com.gym.repository;

import com.gym.model.FitnessTest;
import com.gym.util.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FitnessTestRepository {
    private static final Logger logger = LoggerFactory.getLogger(FitnessTestRepository.class);

    public void save(FitnessTest test) throws SQLException {
        String sql = "INSERT INTO fitness_tests (member_id, coach_id, test_date, push_ups, sit_ups, mile_time, flexibility_cm, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, test.getMemberId());
            pstmt.setInt(2, test.getCoachId());
            pstmt.setDate(3, Date.valueOf(test.getTestDate()));
            pstmt.setInt(4, test.getPushUps());
            pstmt.setInt(5, test.getSitUps());
            pstmt.setString(6, test.getMileTime());
            pstmt.setBigDecimal(7, test.getFlexibilityCm());
            pstmt.setString(8, test.getNotes());
            pstmt.executeUpdate();
            logger.info("Fitness test saved for member: {}", test.getMemberId());
        }
    }

    public FitnessTest findById(int id) throws SQLException {
        String sql = "SELECT * FROM fitness_tests WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToTest(rs);
            }
        }
        return null;
    }

    public List<FitnessTest> findByMemberId(int memberId) throws SQLException {
        List<FitnessTest> tests = new ArrayList<>();
        String sql = "SELECT * FROM fitness_tests WHERE member_id = ? ORDER BY test_date DESC";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tests.add(mapRowToTest(rs));
            }
        }
        return tests;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM fitness_tests WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            logger.info("Fitness test deleted: {}", id);
        }
    }

    private FitnessTest mapRowToTest(ResultSet rs) throws SQLException {
        FitnessTest test = new FitnessTest();
        test.setId(rs.getInt("id"));
        test.setMemberId(rs.getInt("member_id"));
        test.setCoachId(rs.getInt("coach_id"));
        Date testDate = rs.getDate("test_date");
        if (testDate != null) {
            test.setTestDate(testDate.toLocalDate());
        }
        test.setPushUps(rs.getInt("push_ups"));
        test.setSitUps(rs.getInt("sit_ups"));
        test.setMileTime(rs.getString("mile_time"));
        test.setFlexibilityCm(rs.getBigDecimal("flexibility_cm"));
        test.setNotes(rs.getString("notes"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            test.setCreatedAt(createdAt.toLocalDateTime());
        }
        return test;
    }
}