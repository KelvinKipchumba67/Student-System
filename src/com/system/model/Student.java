package com.system.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private String regNo;
    private String programme;
    private List<StudentCourse> registeredCourses;

    public Student(int personId, String firstName, String lastName, String email, String phoneNo, String regNo, String programme) {
        super(personId, firstName, lastName, email, phoneNo);
        this.regNo = regNo;
        this.programme = programme;
        this.registeredCourses = new ArrayList<>();
    }

    // --- Course Registration ---
    public void registerCourse(StudentCourse studentCourse) {
        if (this.registeredCourses.size() >= 5) {
            System.out.println("Registration failed: " + getFirstName() + " is already taking 5 courses.");
            return;
        }

        if (!this.registeredCourses.contains(studentCourse)) {
            this.registeredCourses.add(studentCourse);
            System.out.println("Success: Added " + studentCourse.getCourse().getTitle() + " for " + getFirstName());
        } else {
            System.out.println(getFirstName() + " is already registered for this course.");
        }
    }

    // --- Getters ---
    public String getRegNo() { return regNo; }
    public String getProgramme() { return programme; }
    public List<StudentCourse> getRegisteredCourses() { return registeredCourses; }

    // --- Result Slip Logic ---
    public void printResultSlip() {
        System.out.println("\n=======================================================");
        System.out.println("                 OFFICIAL RESULT SLIP                  ");
        System.out.println("=======================================================");
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Reg No: " + this.regNo + "   |   Programme: " + this.programme);
        System.out.println("-------------------------------------------------------");

        System.out.printf("%-10s %-25s %-10s %-5s\n", "CODE", "TITLE", "SCORE", "GRADE");
        System.out.println("-------------------------------------------------------");

        if (registeredCourses.isEmpty()) {
            System.out.println("No courses registered for this student yet.");
            System.out.println("=======================================================\n");
            return;
        }

        double totalScoreSum = 0;

        for (StudentCourse sc : registeredCourses) {
            String code = sc.getCourse().getCourseCode();
            String title = sc.getCourse().getTitle();
            double score = sc.getScore().getTotalScore();
            String grade = sc.getScore().getGrade();

            System.out.printf("%-10s %-25s %-10.1f %-5s\n", code, title, score, grade);
            totalScoreSum += score;
        }

        double averageScore = totalScoreSum / registeredCourses.size();
        String averageGrade = calculateOverallGrade(averageScore);

        System.out.println("-------------------------------------------------------");
        System.out.printf("%-36s %-10.1f %-5s\n", "AVERAGE:", averageScore, averageGrade);
        System.out.println("=======================================================\n");
    }

    private String calculateOverallGrade(double average) {
        if (average >= 70) return "A";
        if (average >= 60) return "B";
        if (average >= 50) return "C";
        if (average >= 40) return "D";
        return "F";
    }
}