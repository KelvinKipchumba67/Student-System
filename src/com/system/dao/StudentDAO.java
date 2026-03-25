package com.system.dao;

import com.system.model.Student;

public interface StudentDAO {
    void saveStudent(Student student);
    //Search method
    Student searchStudentByRegNo(String regNo);
}