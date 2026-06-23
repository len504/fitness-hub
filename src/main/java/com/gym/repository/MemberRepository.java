package com.gym.repository;

import com.gym.model.Member;
import com.gym.util.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemberRepository.class);

    public void save(Member member) throws SQLException {
        String sql = "INSERT INTO members (name, email, phone, gender, birth_date, fitness_goal, avatar_path) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPhone());
            pstmt.setString(4, member.getGender());
            pstmt.setDate(5, member.getBirthDate() != null ? Date.valueOf(member.getBirthDate()) : null);
            pstmt.setString(6, member.getFitnessGoal());
            pstmt.setString(7, member.getAvatarPath());
            pstmt.executeUpdate();
            logger.info("Member saved: {}", member.getEmail());
        }
    }

    public void update(Member member) throws SQLException {
        String sql = "UPDATE members SET name = ?, phone = ?, gender = ?, birth_date = ?, fitness_goal = ?, avatar_path = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getPhone());
            pstmt.setString(3, member.getGender());
            pstmt.setDate(4, member.getBirthDate() != null ? Date.valueOf(member.getBirthDate()) : null);
            pstmt.setString(5, member.getFitnessGoal());
            pstmt.setString(6, member.getAvatarPath());
            pstmt.setInt(7, member.getId());
            pstmt.executeUpdate();
            logger.info("Member updated: {}", member.getId());
        }
    }

    public Member findById(int id) throws SQLException {
        String sql = "SELECT * FROM members WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToMember(rs);
            }
        }
        return null;
    }

    public Member findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM members WHERE email = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToMember(rs);
            }
        }
        return null;
    }

    public List<Member> findAll() throws SQLException {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members ORDER BY created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                members.add(mapRowToMember(rs));
            }
        }
        return members;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM members WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            logger.info("Member deleted: {}", id);
        }
    }

    private Member mapRowToMember(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setId(rs.getInt("id"));
        member.setName(rs.getString("name"));
        member.setEmail(rs.getString("email"));
        member.setPhone(rs.getString("phone"));
        member.setGender(rs.getString("gender"));
        Date birthDate = rs.getDate("birth_date");
        if (birthDate != null) {
            member.setBirthDate(birthDate.toLocalDate());
        }
        member.setFitnessGoal(rs.getString("fitness_goal"));
        member.setAvatarPath(rs.getString("avatar_path"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            member.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            member.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return member;
    }
}