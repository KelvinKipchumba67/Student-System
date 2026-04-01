package com.system.dao;

import com.system.model.Course;
import com.system.util.DatabaseConnection;
import com.system.model.Lecturer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LecturerDatabaseDAO implements LecturerDAO {

    @Override
    public List<Course> searchAllocatedCourses(int staffNumber) {
        List<Course> foundCourses = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();

        String sql = "SELECT c.course_code, c.title " +
                "FROM Course c " +
                "JOIN Lecturer_course lc ON c.course_code = lc.course_code " +
                "JOIN Lecturer l ON lc.lecturer_id = l.lecturer_id " +
                "WHERE l.Staff_number = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, staffNumber); // Plug the staff number into the '?' in the query

            ResultSet rs = stmt.executeQuery();

            //Loops through the results and creates Course objects in Java
            while (rs.next()) {
                String code = rs.getString("course_code");
                String title = rs.getString("title");

                Course course = new Course(code, title);
                foundCourses.add(course);
            }

        } catch (Exception e) {
            System.out.println("Error searching for courses: " + e.getMessage());
        }

        return foundCourses;
    }

    @Override
    public Lecturer searchLecturerByStaffNo(int staffNumber) {
        Connection conn = DatabaseConnection.getConnection();
        Lecturer foundLecturer = null;

        String sql = "SELECT p.person_id, p.first_name, p.last_name, p.email, p.phone_no, l.Staff_number, l.Department " +
                "FROM Lecturer l " +
                "JOIN Person p ON l.lecturer_id = p.person_id " +
                "WHERE l.Staff_number = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, staffNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                foundLecturer = new Lecturer(
                        rs.getInt("person_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_no"),
                        rs.getInt("Staff_number"),
                        rs.getString("Department")
                );
            }
        } catch (Exception e) {
            System.out.println("Error searching for lecturer: " + e.getMessage());
        }

        return foundLecturer;
    }
}