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
            //Inserts the Person and asks for the generated ID back
            PreparedStatement personStmt = conn.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS);
            personStmt.setString(1, student.getFirstName());
            personStmt.setString(2, student.getLastName());
            personStmt.setString(3, student.getEmail());
            personStmt.setString(4, student.getPhoneNo()); // Assuming you changed this to VARCHAR in DB as discussed!

            personStmt.executeUpdate();

            //Grabs the auto-generated ID from MySQL
            ResultSet rs = personStmt.getGeneratedKeys();
            int newPersonId = -1;
            if (rs.next()) {
                newPersonId = rs.getInt(1); // The generated ID
            }

            //Insert into the Student table using the ID from mysql
            if (newPersonId != -1) {
                PreparedStatement studentStmt = conn.prepareStatement(insertStudentSQL);
                studentStmt.setInt(1, newPersonId);
                studentStmt.setString(2, student.getRegNo());
                studentStmt.setString(3, student.getProgramme());

                studentStmt.executeUpdate();
                System.out.println("Success: " + student.getFirstName() + " was saved to the database");
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

        //join query to get all records at once
        String sql = "SELECT p.person_id, p.first_name, p.last_name, p.email, p.phone_no, s.Reg_no, s.Programme " +
                "FROM Student s " +
                "JOIN Person p ON s.Student_id = p.person_id " +
                "WHERE s.Reg_no = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, regNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
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

        return foundStudent;
    }

    //Method for Lecturers to add Marks to the Student_course table
    public boolean saveStudentMarks(String regNo, String courseCode, String academicYear, String semester, double cat, double exam) {
        java.sql.Connection conn = com.system.util.DatabaseConnection.getConnection();

        //finds the internal student_id using the Reg_no the lecturer typed
        String findStudentSql = "SELECT Student_id FROM Student WHERE Reg_no = ?";
        //inserts the full record into  Student_course
        String insertSql = "INSERT INTO Student_course (student_id, course_code, semester, Academic_year, cat_score, Exam_score) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            //Look up the student
            java.sql.PreparedStatement findStmt = conn.prepareStatement(findStudentSql);
            findStmt.setString(1, regNo);
            java.sql.ResultSet rs = findStmt.executeQuery();

            if (rs.next()) {
                int studentId = rs.getInt("Student_id");

                //inserts the marks.
                java.sql.PreparedStatement insertStmt = conn.prepareStatement(insertSql);//prevents sql injection
                insertStmt.setInt(1, studentId);
                insertStmt.setString(2, courseCode);
                insertStmt.setString(3, semester);
                insertStmt.setString(4, academicYear);
                insertStmt.setDouble(5, cat);
                insertStmt.setDouble(6, exam);

                insertStmt.executeUpdate();
                return true;
            } else {
                return false;//if no student is found
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error saving marks: " + e.getMessage());
            return false;
        }
    }

    //Method to Fetch Marks for the Result Slip
    public java.util.List<Object[]> getStudentMarks(int studentId) {
        java.util.List<Object[]> marksList = new java.util.ArrayList<>();

        String sql = "SELECT sc.course_code, c.title, sc.Academic_year, sc.semester, sc.cat_score, sc.Exam_score " +
                "FROM Student_course sc " +
                "JOIN Course c ON sc.course_code = c.course_code " +
                "WHERE sc.student_id = ?";

        try {
            java.sql.Connection conn = com.system.util.DatabaseConnection.getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentId);
            java.sql.ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String code = rs.getString("course_code");
                String title = rs.getString("title");
                String year = rs.getString("Academic_year");
                String semester = rs.getString("semester");
                double cat = rs.getDouble("cat_score");
                double exam = rs.getDouble("Exam_score");

                marksList.add(new Object[]{code, title, year, semester, cat, exam});
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Error fetching marks: " + e.getMessage());
        }

        return marksList;
    }
}