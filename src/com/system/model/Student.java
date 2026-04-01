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

    //Course Registration
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

    //Getters
    public String getRegNo() { return regNo; }
    public String getProgramme() { return programme; }
    public List<StudentCourse> getRegisteredCourses() { return registeredCourses; }

}