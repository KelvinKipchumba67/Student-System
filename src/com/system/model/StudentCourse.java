package com.system.model;

public class StudentCourse {
    private int id; // Matches the auto-increment ID in the DB
    private Course course;
    private String semester;
    private String academicYear;
    private Score score; // Holds the CAT and Exam scores

    public StudentCourse(int id, Course course, String semester, String academicYear, Score score) {
        this.id = id;
        this.course = course;
        this.semester = semester;
        this.academicYear = academicYear;
        this.score = score;
    }

    public Course getCourse() { return course; }
    public String getSemester() { return semester; }
    public String getAcademicYear() { return academicYear; }
    public Score getScore() { return score; }
}