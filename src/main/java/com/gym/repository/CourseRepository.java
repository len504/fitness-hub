package com.gym.repository;

import com.gym.model.Course;
import com.gym.util.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    private static final Logger logger = LoggerFactory.getLogger(CourseRepository.class);

    public void save(Course course) throws SQLException {
        String sql = "INSERT INTO courses (name, coach_id, category, schedule_date, start_time, end_time, max_capacity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getName());
            pstmt.setInt(2, course.getCoachId());
            pstmt.setString(3, course.getCategory());
            pstmt.setDate(4, Date.valueOf(course.getScheduleDate()));
            pstmt.setTime(5, Time.valueOf(course.getStartTime()));
            pstmt.setTime(6, Time.valueOf(course.getEndTime()));
            pstmt.setInt(7, course.getMaxCapacity());
            pstmt.executeUpdate();
            logger.info("Course saved: {}", course.getName());
        }
    }

    public void update(Course course) throws SQLException {
        String sql = "UPDATE courses SET name = ?, category = ?, schedule_date = ?, start_time = ?, end_time = ?, max_capacity = ?, current_enrollment = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getName());
            pstmt.setString(2, course.getCategory());
            pstmt.setDate(3, Date.valueOf(course.getScheduleDate()));
            pstmt.setTime(4, Time.valueOf(course.getStartTime()));
            pstmt.setTime(5, Time.valueOf(course.getEndTime()));
            pstmt.setInt(6, course.getMaxCapacity());
            pstmt.setInt(7, course.getCurrentEnrollment());
            pstmt.setInt(8, course.getId());
            pstmt.executeUpdate();
            logger.info("Course updated: {}", course.getId());
        }
    }

    public Course findById(int id) throws SQLException {
        String sql = "SELECT c.*, coach.name as coach_name FROM courses c LEFT JOIN coaches coach ON c.coach_id = coach.id WHERE c.id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToCourse(rs);
            }
        }
        return null;
    }

    public List<Course> findAll() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.*, coach.name as coach_name FROM courses c LEFT JOIN coaches coach ON c.coach_id = coach.id ORDER BY c.schedule_date DESC";
        try (Connection conn = DatabaseConfig.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                courses.add(mapRowToCourse(rs));
            }
        }
        return courses;
    }

    public List<Course> findByCoachId(int coachId) throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.*, coach.name as coach_name FROM courses c LEFT JOIN coaches coach ON c.coach_id = coach.id WHERE c.coach_id = ? ORDER BY c.schedule_date DESC";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, coachId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                courses.add(mapRowToCourse(rs));
            }
        }
        return courses;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            logger.info("Course deleted: {}", id);
        }
    }

    private Course mapRowToCourse(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setId(rs.getInt("id"));
        course.setName(rs.getString("name"));
        course.setCoachId(rs.getInt("coach_id"));
        course.setCoachName(rs.getString("coach_name"));
        course.setCategory(rs.getString("category"));
        Date scheduleDate = rs.getDate("schedule_date");
        if (scheduleDate != null) {
            course.setScheduleDate(scheduleDate.toLocalDate());
        }
        Time startTime = rs.getTime("start_time");
        if (startTime != null) {
            course.setStartTime(startTime.toLocalTime());
        }
        Time endTime = rs.getTime("end_time");
        if (endTime != null) {
            course.setEndTime(endTime.toLocalTime());
        }
        course.setMaxCapacity(rs.getInt("max_capacity"));
        course.setCurrentEnrollment(rs.getInt("current_enrollment"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            course.setCreatedAt(createdAt.toLocalDateTime());
        }
        return course;
    }
}