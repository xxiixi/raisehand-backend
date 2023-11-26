package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.AddStudentFeedbackReq;
import com.gentry.raisehand.Req.GetStudentCourseReq;
import com.gentry.raisehand.entity.Student;
import com.gentry.raisehand.entity.StudentCourse;
import com.gentry.raisehand.service.StudentCourseService;
import com.gentry.raisehand.util.RestResult;
import com.gentry.raisehand.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation("StudentCourse get")
    @PostMapping(value = "/getStudentCourse")
    public RestResult getStudentCourse(@RequestBody GetStudentCourseReq getStudentCourseReq){
        QueryWrapper<StudentCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",getStudentCourseReq.getStudentId());
        List<StudentCourse>studentCourseList=studentCourseService.list(queryWrapper);
        return ResultUtils.success(studentCourseList);
    }

}

