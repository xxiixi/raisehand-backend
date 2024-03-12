package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.*;
import com.gentry.raisehand.Res.AddCourseRes;
import com.gentry.raisehand.Res.GetCourseShareRes;
import com.gentry.raisehand.Res.GetStudentCourseRes;
import com.gentry.raisehand.entity.Course;
import com.gentry.raisehand.entity.Lecture;
import com.gentry.raisehand.entity.TeacherCourse;
import com.gentry.raisehand.service.CourseService;
import com.gentry.raisehand.service.LectureService;
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
import java.util.UUID;

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
    @Autowired
    private LectureService lectureService;
    @ApiOperation("course add")
    @PostMapping(value = "/addCourse")
    public RestResult addCourse(@RequestBody AddCourseReq addCourseReq){
        Course course=new Course();
        course.setCourseName(addCourseReq.getCourseName());
        course.setCourseCode(addCourseReq.getCourseCode());
        course.setCourseSemester(addCourseReq.getCourseSemester());
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
            Course course=courseService.getById(teacherCourse.getCourseId());
            if (course !=null){
                courseList.add(course);
            }
        }
        return ResultUtils.success(courseList);
    }
    @ApiOperation("the same name but not same semester course get ")
    @PostMapping(value = "/getCourseSemester")
    public RestResult getCourseSemester(@RequestBody GetCourseSemesterReq getCourseSemesterReq){
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("course_name",getCourseSemesterReq.getCourseName())
                .eq("course_code",getCourseSemesterReq.getCourseCode());
        List<Course> courseList=courseService.list(queryWrapper);
        List<Course> courseList1=new ArrayList<>();
        for(Course course:courseList){
            QueryWrapper<TeacherCourse>teacherCourseQueryWrapper=new QueryWrapper<>();
            teacherCourseQueryWrapper
                    .eq("teacher_id",getCourseSemesterReq.getTeacherId())
                    .eq("course_id",course.getId());
            if (teacherCourseService.getOne(teacherCourseQueryWrapper) != null){
                courseList1.add(course);
            }
        }
        List<GetStudentCourseRes>getStudentCourseResList=new ArrayList<>();
        for(Course course:courseList1){
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
    @ApiOperation("search course share")
    @PostMapping(value = "/getCourseShare")
    public RestResult getCourseShare(@RequestBody GetCourseShareReq getCourseShareReq){
        Course course =courseService.getById(getCourseShareReq.getCourseId());
        GetCourseShareRes getCourseShareRes=new GetCourseShareRes();
        if(course.getShareStatus().equals("shared")){
            getCourseShareRes.setShareId(course.getShareId());
            getCourseShareRes.setShareStatus(course.getShareStatus());
        } else if (course.getShareStatus().equals("stop")){
            getCourseShareRes.setShareStatus(course.getShareStatus());
        } else {
            getCourseShareRes.setShareStatus("stop");
        }
        return ResultUtils.success(getCourseShareRes);
    }
    @ApiOperation("set course share")
    @PostMapping(value = "/setCourseShare")
    public RestResult setCourseShare(@RequestBody SetCourseShareReq setCourseShareReq){
        Course course =courseService.getById(setCourseShareReq.getCourseId());
        GetCourseShareRes getCourseShareRes=new GetCourseShareRes();
        if(course.getShareStatus() == null){
            course.setShareStatus("shared");
            course.setShareId(UUID.randomUUID().toString());
            courseService.updateById(course);
            getCourseShareRes.setShareId(course.getShareId());
        }
        if(course.getShareStatus().equals("shared")){
            courseService.updateById(course.setShareStatus("stop"));
        } else if(course.getShareStatus().equals("stop")){
            courseService.updateById(course.setShareStatus("shared"));
            getCourseShareRes.setShareId(course.getShareId());
        }
        getCourseShareRes.setShareStatus(course.getShareStatus());
        return ResultUtils.success(getCourseShareRes);
    }
}

