package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.AddStudentFeedbackReq;
import com.gentry.raisehand.Req.GetCourseFromStudentReq;
import com.gentry.raisehand.Req.GetStudentCourseReq;
import com.gentry.raisehand.Res.CourseFromStudentRes;
import com.gentry.raisehand.Res.GetStudentCourseRes;
import com.gentry.raisehand.entity.Course;
import com.gentry.raisehand.entity.Lecture;
import com.gentry.raisehand.entity.Student;
import com.gentry.raisehand.entity.StudentCourse;
import com.gentry.raisehand.service.CourseService;
import com.gentry.raisehand.service.LectureService;
import com.gentry.raisehand.service.StudentCourseService;
import com.gentry.raisehand.service.StudentService;
import com.gentry.raisehand.util.RestResult;
import com.gentry.raisehand.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lyt
 * @since 2023-11-26
 */
@CrossOrigin
@Api(tags = "StudentCourse get")
@RestController
@RequestMapping("/gentry/raisehand/student-course")
public class StudentCourseController {
    @Autowired
    private StudentCourseService studentCourseService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private LectureService lectureService;
    @Autowired
    private StudentService studentService;
    @ApiOperation("StudentCourse get")
    @PostMapping(value = "/getStudentCourse")
    public RestResult getStudentCourse(@RequestBody GetStudentCourseReq getStudentCourseReq){
        QueryWrapper<StudentCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",getStudentCourseReq.getStudentId());
        List<StudentCourse>studentCourseList=studentCourseService.list(queryWrapper);
        List<Course>courseList=new ArrayList<>();
        for(StudentCourse studentCourse:studentCourseList){
            Course course=courseService.getById(studentCourse.getCourseId());
            if ( course != null){
                courseList.add(course);
            }
        }
        List<GetStudentCourseRes>getStudentCourseResList=new ArrayList<>();
        for(Course course:courseList){
            QueryWrapper<Lecture> lectureQueryWrapper = new QueryWrapper<>();
            lectureQueryWrapper.eq("course_id",course.getId());
            List<Lecture>lectureList=lectureService.list(lectureQueryWrapper);
            GetStudentCourseRes getStudentCourseRes=new GetStudentCourseRes();
            getStudentCourseRes.setCourse(course);
            getStudentCourseRes.setLectureList(lectureList);
            getStudentCourseResList.add(getStudentCourseRes);
        }
        return ResultUtils.success(getStudentCourseResList);
    }

    @ApiOperation("get course from student")
    @PostMapping(value = "/getCourseFromStudent")
    public RestResult getCourseFromStudent(@RequestBody GetCourseFromStudentReq getCourseFromStudentReq ){

        QueryWrapper<StudentCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",getCourseFromStudentReq.getCourseId());

        List<StudentCourse>studentCourseList = studentCourseService.list(queryWrapper);

        List<CourseFromStudentRes>courseFromStudentResList = new ArrayList<>();

        for(StudentCourse studentCourse:studentCourseList){
            CourseFromStudentRes courseFromStudentRes = new CourseFromStudentRes();
            Student student = studentService.getById(studentCourse.getStudentId());
            if ( student != null){
                courseFromStudentRes.setName(student.getStudentName());
                courseFromStudentRes.setUserid(student.getId());
                courseFromStudentRes.setAvatarUrl(student.getStudentAvatarUrl());

                courseFromStudentResList.add(courseFromStudentRes);
            }
        }
        return ResultUtils.success(courseFromStudentResList);
    }

}

