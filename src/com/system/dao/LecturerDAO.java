package com.system.dao;

import com.system.model.Course;
import com.system.model.Lecturer;
import java.util.List;

public interface LecturerDAO {
    List<Course> searchAllocatedCourses(int staffNumber);
    Lecturer searchLecturerByStaffNo(int staffNumber);
}