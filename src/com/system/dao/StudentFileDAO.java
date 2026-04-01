package com.system.dao;

import com.system.model.Student;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class StudentFileDAO implements StudentDAO {

    private static final String FILE_PATH = "students_data.txt";

    @Override
    public void saveStudent(Student student) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            String studentData = String.format("%d,%s,%s,%s,%s,%s,%s",
                    student.getPersonId(),
                    student.getFirstName(),
                    student.getLastName(),
                    student.getEmail(),
                    student.getPhoneNo(),
                    student.getRegNo(),
                    student.getProgramme());

            writer.println(studentData);
            System.out.println("Success: " + student.getFirstName() + " was saved to the text file!");

        } catch (IOException e) {
            System.out.println("Critical Error: Could not write to file.");
            e.printStackTrace();
        }
    }

    @Override
    public Student searchStudentByRegNo(String regNo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // ID(0), FirstName(1), LastName(2), Email(3), Phone(4), RegNo(5), Programme(6)
                if (data.length == 7 && data[5].equals(regNo)) {
                    return new Student(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4], data[5], data[6]);
                }
            }
        } catch (IOException e) {
        }
        return null; // Returns null if the student isn't found
    }
}