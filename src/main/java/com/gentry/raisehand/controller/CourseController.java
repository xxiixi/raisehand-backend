package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.AddCourseReq;
import com.gentry.raisehand.Req.DeleteCourseReq;
import com.gentry.raisehand.Req.GetCourseReq;
import com.gentry.raisehand.Res.AddCourseRes;
import com.gentry.raisehand.entity.Course;
import com.gentry.raisehand.entity.TeacherCourse;
import com.gentry.raisehand.service.CourseService;
import com.gentry.raisehand.service.TeacherCourseService;
import com.gentry.raisehand.util.RestResult;
import com.gentry.raisehand.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * @since 2023-11-25
 */
@CrossOrigin
@Api(tags = "Course add delete get")
@RestController
@RequestMapping("/gentry/raisehand/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherCourseService teacherCourseService;
    @ApiOperation("course add")
    @PostMapping(value = "/addCourse")
    public RestResult addCourse(@RequestBody AddCourseReq addCourseReq){
        Course course=new Course();
        course.setCourseName(addCourseReq.getCourseName());
        course.setCourseCode(addCourseReq.getCourseCode());
        course.setCourseSemster(addCourseReq.getCourseSemster());
        courseService.save(course);
        TeacherCourse teacherCourse =new TeacherCourse();
        teacherCourse.setTeacherId(addCourseReq.getTeacherId());
        teacherCourse.setCourseId(course.getId());
        teacherCourseService.save(teacherCourse);
        AddCourseRes addCourseRes= new AddCourseRes();
        addCourseRes.setCourse(course);
        addCourseRes.setTeacherCourse(teacherCourse);
        return ResultUtils.success(addCourseRes);
    }
    @ApiOperation("course delete")
    @PostMapping(value = "/deleteCourse")
    public RestResult deleteCourse(@RequestBody DeleteCourseReq deleteCourseReq){
        QueryWrapper<TeacherCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("teacher_id",deleteCourseReq.getTeacherId())
                .eq("course_id",deleteCourseReq.getCourseId());
        TeacherCourse teacherCourse=teacherCourseService.getOne(queryWrapper);
        TeacherController teacherController=new TeacherController();
        int loginStatus=teacherController.checkTeacherFunction(deleteCourseReq.getTeacherId(),deleteCourseReq.getToken());
        if (loginStatus == 1&&teacherCourse !=null){
            courseService.removeById(teacherCourse.getId());
        }else {
            return ResultUtils.error(loginStatus,"error");
        }
        return ResultUtils.success(teacherCourse);
    }
    @ApiOperation("course get")
    @PostMapping(value = "/getCourse")
    public RestResult getCourse(@RequestBody GetCourseReq getCourseReq){
        QueryWrapper<TeacherCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("teacher_id",getCourseReq.getTeacherId());
        List<TeacherCourse> teacherCourses=teacherCourseService.list(queryWrapper);
        List<Course> courseList=new ArrayList<>();
        for (TeacherCourse teacherCourse:teacherCourses){
            courseList.add(courseService.getById(teacherCourse.getCourseId()));
        }
        return ResultUtils.success(courseList);
    }

}

