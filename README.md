# Fitness Hub - Gym Management System

A comprehensive desktop-based gym and fitness management system built with **Java** and **JavaFX**, featuring member management, course reservation, training plans, fitness tracking, and more.

## Features

- **Member Registration & Management** - Register members, manage personal information, upload avatars
- **Course Reservation & Administration** - Browse and book courses, manage gym timetable
- **Custom Training Plans** - Create and manage personalized training programs
- **Training Logging & Check-in** - Log workouts, track exercises, calculate calories burned
- **Physical Index Tracking** - Monitor body measurements and fitness progress
- **Fitness Test Reports** - Record and analyze fitness test results
- **Notification System** - Reminders for courses, training plans, and hydration
- **Administrator Backend** - Manage membership packages, pricing, and revenue

## Technology Stack

- **Java 17+** - Core programming language
- **JavaFX 21** - GUI framework for desktop application
- **MySQL 8.0** - Relational database
- **Maven** - Build and dependency management
- **SLF4J + Logback** - Logging framework

## Project Structure

```
fitness-hub/
├── src/
│   ├── main/
│   │   ├── java/com/gym/
│   │   │   ├── App.java
│   │   │   ├── model/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── ui/
│   │   │   └── util/
│   │   └── resources/
│   └── test/
└── pom.xml
```

## Prerequisites

- Java 17 or higher
- Maven 3.8+
- MySQL 8.0+

## Quick Start

1. Clone the repository
2. Create MySQL database: `CREATE DATABASE fitness_hub;`
3. Update DB credentials in `DatabaseConfig.java`
4. Build: `mvn clean install`
5. Run: `mvn javafx:run`

## License

MIT License
