package com.gym.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fitness_hub";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password"; // Change as needed

    /**
     * Get database connection
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            logger.error("MySQL JDBC Driver not found", e);
            throw new SQLException("Database driver not found", e);
        }
    }

    /**
     * Initialize database schema
     */
    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            logger.info("Initializing database schema...");

            // Create Members table
            stmt.execute("CREATE TABLE IF NOT EXISTS members (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "email VARCHAR(100) UNIQUE NOT NULL," +
                    "phone VARCHAR(20)," +
                    "gender VARCHAR(10)," +
                    "birth_date DATE," +
                    "fitness_goal VARCHAR(255)," +
                    "avatar_path VARCHAR(255)," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                    ")");

            // Create Coaches table
            stmt.execute("CREATE TABLE IF NOT EXISTS coaches (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "email VARCHAR(100) UNIQUE NOT NULL," +
                    "phone VARCHAR(20)," +
                    "specialization VARCHAR(255)," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");

            // Create Courses table
            stmt.execute("CREATE TABLE IF NOT EXISTS courses (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "coach_id INT NOT NULL," +
                    "category VARCHAR(50)," +
                    "schedule_date DATE NOT NULL," +
                    "start_time TIME NOT NULL," +
                    "end_time TIME NOT NULL," +
                    "max_capacity INT," +
                    "current_enrollment INT DEFAULT 0," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (coach_id) REFERENCES coaches(id)" +
                    ")");

            // Create Course Reservations table
            stmt.execute("CREATE TABLE IF NOT EXISTS course_reservations (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "member_id INT NOT NULL," +
                    "course_id INT NOT NULL," +
                    "reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "status VARCHAR(20) DEFAULT 'confirmed'," +
                    "FOREIGN KEY (member_id) REFERENCES members(id)," +
                    "FOREIGN KEY (course_id) REFERENCES courses(id)" +
                    ")");

            // Create Training Plans table
            stmt.execute("CREATE TABLE IF NOT EXISTS training_plans (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "member_id INT NOT NULL," +
                    "coach_id INT NOT NULL," +
                    "plan_name VARCHAR(100)," +
                    "start_date DATE NOT NULL," +
                    "end_date DATE," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (member_id) REFERENCES members(id)," +
                    "FOREIGN KEY (coach_id) REFERENCES coaches(id)" +
                    ")");

            // Create Training Exercises table
            stmt.execute("CREATE TABLE IF NOT EXISTS training_exercises (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "plan_id INT NOT NULL," +
                    "exercise_name VARCHAR(100) NOT NULL," +
                    "sets INT," +
                    "repetitions INT," +
                    "weight DECIMAL(10, 2)," +
                    "rest_interval INT," +
                    "notes TEXT," +
                    "FOREIGN KEY (plan_id) REFERENCES training_plans(id)" +
                    ")");

            // Create Training Logs table
            stmt.execute("CREATE TABLE IF NOT EXISTS training_logs (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "member_id INT NOT NULL," +
                    "log_date DATE NOT NULL," +
                    "exercise_name VARCHAR(100)," +
                    "sets_completed INT," +
                    "reps_completed INT," +
                    "weight_used DECIMAL(10, 2)," +
                    "duration_minutes INT," +
                    "calories_burned DECIMAL(10, 2)," +
                    "feelings VARCHAR(255)," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (member_id) REFERENCES members(id)" +
                    ")");

            // Create Physical Data table
            stmt.execute("CREATE TABLE IF NOT EXISTS physical_data (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "member_id INT NOT NULL," +
                    "measurement_date DATE NOT NULL," +
                    "weight DECIMAL(10, 2)," +
                    "body_fat_percentage DECIMAL(5, 2)," +
                    "chest_cm DECIMAL(10, 2)," +
                    "waist_cm DECIMAL(10, 2)," +
                    "hip_cm DECIMAL(10, 2)," +
                    "bicep_cm DECIMAL(10, 2)," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (member_id) REFERENCES members(id)" +
                    ")");

            // Create Fitness Tests table
            stmt.execute("CREATE TABLE IF NOT EXISTS fitness_tests (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "member_id INT NOT NULL," +
                    "coach_id INT NOT NULL," +
                    "test_date DATE NOT NULL," +
                    "push_ups INT," +
                    "sit_ups INT," +
                    "mile_time VARCHAR(20)," +
                    "flexibility_cm DECIMAL(10, 2)," +
                    "notes TEXT," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (member_id) REFERENCES members(id)," +
                    "FOREIGN KEY (coach_id) REFERENCES coaches(id)" +
                    ")");

            // Create Notifications table
            stmt.execute("CREATE TABLE IF NOT EXISTS notifications (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "member_id INT NOT NULL," +
                    "notification_type VARCHAR(50)," +
                    "message TEXT," +
                    "is_read BOOLEAN DEFAULT FALSE," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (member_id) REFERENCES members(id)" +
                    ")");

            logger.info("Database schema initialized successfully");
        } catch (SQLException e) {
            logger.error("Error initializing database", e);
        }
    }

    /**
     * Test database connection
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            logger.info("Database connection successful");
            return true;
        } catch (SQLException e) {
            logger.error("Database connection failed", e);
            return false;
        }
    }
}