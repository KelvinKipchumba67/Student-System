package com.system.dao;

import com.system.model.Course;
import com.system.model.Lecturer; // Make sure to import Lecturer
import java.util.List;

public interface LecturerDAO {
    List<Course> searchAllocatedCourses(int staffNumber);
    //Search method

    Lecturer searchLecturerByStaffNo(int staffNumber);
}