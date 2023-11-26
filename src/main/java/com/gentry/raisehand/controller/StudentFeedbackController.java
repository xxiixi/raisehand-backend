package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.AddStudentFeedbackReq;
import com.gentry.raisehand.Req.GetStudentFeedbackSReq;
import com.gentry.raisehand.Req.GetStudentFeedbackTReq;
import com.gentry.raisehand.entity.Student;
import com.gentry.raisehand.entity.StudentFeedback;
import com.gentry.raisehand.service.StudentFeedbackService;
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
@Api(tags = "StudentFeedback add/StudentFeedback get by teacher and student")
@RestController
@RequestMapping("/gentry/raisehand/student-feedback")
public class StudentFeedbackController {
    @Autowired
    private StudentFeedbackService studentFeedbackService;
    @ApiOperation("StudentFeedback add")
    @PostMapping(value = "/addStudentFeedback")
    public RestResult addStudentFeedback(@RequestBody AddStudentFeedbackReq addStudentFeedbackReq){
        StudentFeedback studentFeedback=new StudentFeedback();
        studentFeedback
                .setCourseId(addStudentFeedbackReq.getCourseId())
                .setStudentId(addStudentFeedbackReq.getStudentId())
                .setFeedbackContent(addStudentFeedbackReq.getFeedbackContent())
                .setFeedbackStatus(addStudentFeedbackReq.getFeedbackStatus());
        studentFeedbackService.save(studentFeedback);
        return ResultUtils.success(studentFeedback);
    }
    @ApiOperation("StudentFeedback get by teacher")
    @PostMapping(value = "/getStudentFeedbackT")
    public RestResult getStudentFeedbackT(@RequestBody GetStudentFeedbackTReq getStudentFeedbackTReq){
        QueryWrapper<StudentFeedback> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",getStudentFeedbackTReq.getCourseId());
        List<StudentFeedback>studentFeedbackList=studentFeedbackService.list(queryWrapper);
        return ResultUtils.success(studentFeedbackList);
    }
    @ApiOperation("StudentFeedback get by student")
    @PostMapping(value = "/getStudentFeedbackS")
    public RestResult getStudentFeedbackS(@RequestBody GetStudentFeedbackSReq getStudentFeedbackSReq){
        QueryWrapper<StudentFeedback> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",getStudentFeedbackSReq.getStudentId());
        List<StudentFeedback>studentFeedbackList=studentFeedbackService.list(queryWrapper);
        return ResultUtils.success(studentFeedbackList);
    }
}

