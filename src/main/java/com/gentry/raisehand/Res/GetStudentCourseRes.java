package com.gentry.raisehand.Res;

import com.gentry.raisehand.entity.Course;
import com.gentry.raisehand.entity.Lecture;
import lombok.Data;

import java.util.List;

@Data
public class GetStudentCourseRes {
    private Course course;
    private List<Lecture>lectureList;
}
