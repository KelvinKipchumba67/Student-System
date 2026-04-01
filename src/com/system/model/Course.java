package com.system.model;

public class Course {
    private String courseCode;
    private String title;

    public Course(String courseCode, String title) {
        this.courseCode = courseCode;
        this.title = title;
    }

    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }

    @Override
    public String toString() {
        return courseCode + " - " + title;
    }
}