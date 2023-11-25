package com.gentry.raisehand.Res;

import com.gentry.raisehand.entity.Course;
import com.gentry.raisehand.entity.TeacherCourse;
import lombok.Data;

@Data
public class AddCourseRes {
    private Course course;
    private TeacherCourse teacherCourse;
}
