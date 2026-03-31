package com.system.dao;

import com.system.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddLecturerDAO {

    // Added 'semester' to the parameters!
    public boolean addLecturerAndCourse(String fName, String lName, String email, String phone, int staffNo, String dept, String courseCode, String semester, String Academic_year) {
        String insertPerson = "INSERT INTO Person (first_name, last_name, email, phone_no) VALUES (?, ?, ?, ?)";
        String insertLecturer = "INSERT INTO Lecturer (lecturer_id, Staff_number, Department) VALUES (?, ?, ?)";

        // Updated this SQL statement to include the semester column!
        String assignCourse = "INSERT INTO Lecturer_Course (lecturer_id, course_code, semester, Academic_year) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();

            // 1. Create the Person record
            PreparedStatement personStmt = conn.prepareStatement(insertPerson, Statement.RETURN_GENERATED_KEYS);
            personStmt.setString(1, fName);
            personStmt.setString(2, lName);
            personStmt.setString(3, email);
            personStmt.setString(4, phone);
            personStmt.executeUpdate();

            // 2. Get the new ID and create the Lecturer record
            ResultSet rs = personStmt.getGeneratedKeys();
            if (rs.next()) {
                int personId = rs.getInt(1);

                PreparedStatement lecStmt = conn.prepareStatement(insertLecturer);
                lecStmt.setInt(1, personId);
                lecStmt.setInt(2, staffNo);
                lecStmt.setString(3, dept);
                lecStmt.executeUpdate();

                // 3. Assign the Course AND Semester
                if (!courseCode.isEmpty()) {
                    PreparedStatement courseStmt = conn.prepareStatement(assignCourse);
                    courseStmt.setInt(1, personId);
                    courseStmt.setString(2, courseCode);
                    courseStmt.setString(3, semester);// Added the semester parameter here
                    courseStmt.setString(4, Academic_year);
                    courseStmt.executeUpdate();
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Database Error: " + e.getMessage());
            return false;
        }
    }

    // 1. Fetches the Lecturer's Profile Data
    public Object[] getLecturerProfile(int staffNo) {
        String sql = "SELECT p.first_name, p.last_name, l.Department, l.lecturer_id " +
                "FROM Person p " +
                "JOIN Lecturer l ON p.person_id = l.lecturer_id " +
                "WHERE l.Staff_number = ?";


        try {
            java.sql.Connection conn = DatabaseConnection.getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, staffNo);
            java.sql.ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Object[]{
                        rs.getString("first_name") + " " + rs.getString("last_name"), // Full Name
                        rs.getString("Department"),                                   // Department
                        rs.getInt("lecturer_id")                                      // Internal DB ID
                };
            }
        } catch (Exception e) {
            System.err.println("🚨 Error fetching Lecturer Profile: " + e.getMessage());
        }
        return null; // Return null if staff number doesn't exist
    }

    // 2. Fetches the exact courses assigned to them
    public java.util.List<Object[]> getLecturerCourses(int lecturerId) {
        java.util.List<Object[]> courses = new java.util.ArrayList<>();
        String sql = "SELECT course_code, semester FROM Lecturer_Course WHERE lecturer_id = ?";

        // FIX: Removed the parentheses here as well!
        try {
            java.sql.Connection conn = DatabaseConnection.getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, lecturerId);
            java.sql.ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courses.add(new Object[]{
                        rs.getString("course_code"),
                        rs.getString("semester")
                });
            }
        } catch (Exception e) {
            System.err.println("Error fetching Lecturer Courses: " + e.getMessage());
        }
        return courses;
    }
}