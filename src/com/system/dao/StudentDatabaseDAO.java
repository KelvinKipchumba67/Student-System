package com.system.dao;

import com.system.model.Student;
import com.system.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class StudentDatabaseDAO implements StudentDAO {

    @Override
    public void saveStudent(Student student) {
        Connection conn = DatabaseConnection.getConnection();

        // The two SQL queries we need to run
        String insertPersonSQL = "INSERT INTO Person (first_name, last_name, email, phone_no) VALUES (?, ?, ?, ?)";
        String insertStudentSQL = "INSERT INTO Student (Student_id, Reg_no, Programme) VALUES (?, ?, ?)";

        try {
            //Insert the Person and ask for the generated ID back
            PreparedStatement personStmt = conn.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS);
            personStmt.setString(1, student.getFirstName());
            personStmt.setString(2, student.getLastName());
            personStmt.setString(3, student.getEmail());
            personStmt.setString(4, student.getPhoneNo()); // Assuming you changed this to VARCHAR in DB as discussed!

            personStmt.executeUpdate();

            //Grab the auto-generated ID from MySQL
            ResultSet rs = personStmt.getGeneratedKeys();
            int newPersonId = -1;
            if (rs.next()) {
                newPersonId = rs.getInt(1); // The generated ID
            }

            //Insert into the Student table using the ID we just got
            if (newPersonId != -1) {
                PreparedStatement studentStmt = conn.prepareStatement(insertStudentSQL);
                studentStmt.setInt(1, newPersonId);
                studentStmt.setString(2, student.getRegNo());
                studentStmt.setString(3, student.getProgramme());

                studentStmt.executeUpdate();
                System.out.println("Success: " + student.getFirstName() + " was saved to the database!");
            }

        } catch (Exception e) {
            System.out.println("Error saving student to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Student searchStudentByRegNo(String regNo) {
        Connection conn = DatabaseConnection.getConnection();
        Student foundStudent = null;

        // We JOIN the Student table with the Person table to get all their details at once
        String sql = "SELECT p.person_id, p.first_name, p.last_name, p.email, p.phone_no, s.Reg_no, s.Programme " +
                "FROM Student s " +
                "JOIN Person p ON s.Student_id = p.person_id " +
                "WHERE s.Reg_no = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, regNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // If we found them, build a new Student object from the database row!
                foundStudent = new Student(
                        rs.getInt("person_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_no"),
                        rs.getString("Reg_no"),
                        rs.getString("Programme")
                );
            }
        } catch (Exception e) {
            System.out.println("Error searching for student: " + e.getMessage());
        }

        return null;
    }
}