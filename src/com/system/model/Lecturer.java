package com.system.model;

import java.util.ArrayList;
import java.util.List;

public class Lecturer extends Person {
    private int staffNumber;
    private String department;
    private List<Course> allocatedCourses; //Maps to the Lecturer_course table

    public Lecturer(int personId, String firstName, String lastName, String email, String phoneNo, int staffNumber, String department) {
        super(personId, firstName, lastName, email, phoneNo);
        this.staffNumber = staffNumber;
        this.department = department;
        this.allocatedCourses = new ArrayList<>();
    }

    //Allocating a course to a lecturer
    public void allocateCourse(Course course) {
        if (!allocatedCourses.contains(course)) {
            allocatedCourses.add(course);
        }
    }

    public int getStaffNumber() { return staffNumber; }
    public String getDepartment() { return department; }
    public List<Course> getAllocatedCourses() { return allocatedCourses; }
}