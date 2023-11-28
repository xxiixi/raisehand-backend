package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.CheckStudetReq;
import com.gentry.raisehand.Req.CheckTeacherReq;
import com.gentry.raisehand.Req.LoginReq;
import com.gentry.raisehand.Res.LoginRes;
import com.gentry.raisehand.entity.Student;
import com.gentry.raisehand.service.StudentService;
import com.gentry.raisehand.util.RestResult;
import com.gentry.raisehand.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import redis.clients.jedis.Jedis;

import static java.util.Objects.hash;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lyt
 * @since 2023-11-13
 */
@CrossOrigin
@Api(tags = "Student check (login is in teacher)")
@RestController
@RequestMapping("/gentry/raisehand/student")
public class StudentController {

    @ApiOperation("check student login")
    @PostMapping(value = "/checkStudent")
    public RestResult checkStudent(@RequestBody CheckStudetReq checkstudentReq){
        TeacherController teacherController=new TeacherController();
        return ResultUtils.success(teacherController.checkTeacherFunction(checkstudentReq.getStudentId(),checkstudentReq.getToken()));

    }

}

