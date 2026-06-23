package com.gym.repository;

import com.gym.model.PhysicalData;
import com.gym.util.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PhysicalDataRepository {
    private static final Logger logger = LoggerFactory.getLogger(PhysicalDataRepository.class);

    public void save(PhysicalData data) throws SQLException {
        String sql = "INSERT INTO physical_data (member_id, measurement_date, weight, body_fat_percentage, chest_cm, waist_cm, hip_cm, bicep_cm) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, data.getMemberId());
            pstmt.setDate(2, Date.valueOf(data.getMeasurementDate()));
            pstmt.setBigDecimal(3, data.getWeight());
            pstmt.setBigDecimal(4, data.getBodyFatPercentage());
            pstmt.setBigDecimal(5, data.getChestCm());
            pstmt.setBigDecimal(6, data.getWaistCm());
            pstmt.setBigDecimal(7, data.getHipCm());
            pstmt.setBigDecimal(8, data.getBicepCm());
            pstmt.executeUpdate();
            logger.info("Physical data saved for member: {}", data.getMemberId());
        }
    }

    public PhysicalData findById(int id) throws SQLException {
        String sql = "SELECT * FROM physical_data WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToData(rs);
            }
        }
        return null;
    }

    public List<PhysicalData> findByMemberId(int memberId) throws SQLException {
        List<PhysicalData> dataList = new ArrayList<>();
        String sql = "SELECT * FROM physical_data WHERE member_id = ? ORDER BY measurement_date DESC";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dataList.add(mapRowToData(rs));
            }
        }
        return dataList;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM physical_data WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            logger.info("Physical data deleted: {}", id);
        }
    }

    private PhysicalData mapRowToData(ResultSet rs) throws SQLException {
        PhysicalData data = new PhysicalData();
        data.setId(rs.getInt("id"));
        data.setMemberId(rs.getInt("member_id"));
        Date measurementDate = rs.getDate("measurement_date");
        if (measurementDate != null) {
            data.setMeasurementDate(measurementDate.toLocalDate());
        }
        data.setWeight(rs.getBigDecimal("weight"));
        data.setBodyFatPercentage(rs.getBigDecimal("body_fat_percentage"));
        data.setChestCm(rs.getBigDecimal("chest_cm"));
        data.setWaistCm(rs.getBigDecimal("waist_cm"));
        data.setHipCm(rs.getBigDecimal("hip_cm"));
        data.setBicepCm(rs.getBigDecimal("bicep_cm"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            data.setCreatedAt(createdAt.toLocalDateTime());
        }
        return data;
    }
}