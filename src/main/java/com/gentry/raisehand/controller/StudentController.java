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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
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
@Api(tags = "Student check (login is in teacher)")
@RestController
@RequestMapping("/gentry/raisehand/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    public RestResult studentLogin(LoginReq loginReq){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("student_email",loginReq.getEmail())
                .eq("student_password",hash(loginReq.getPassword()+"raisehand"));
        Student student=studentService.getOne(queryWrapper);
        if (student == null){
            return ResultUtils.error(0,"wrong");
        }else {
            LoginRes loginRes = new LoginRes();
            loginRes.setUserId(student.getId());
            loginRes.setStatus("student");
            Jedis jedis = new Jedis("127.0.0.1", 6379);
            jedis.set(String.valueOf(student.getId()), String.valueOf(hash(student.getStudentPassword() + "raisehand")));
            jedis.expire(String.valueOf(student.getId()), 1728000);
            loginRes.setToken(String.valueOf(hash(student.getStudentPassword() + "raisehand")));
            return ResultUtils.success(loginRes);
        }
    }
    @ApiOperation("check student login")
    @PostMapping(value = "/checkStudent")
    public RestResult checkStudent(@RequestBody CheckStudetReq checkstudentReq){
        TeacherController teacherController=new TeacherController();
        return ResultUtils.success(teacherController.checkTeacherFunction(checkstudentReq.getStudentId(),checkstudentReq.getToken()));

    }

}

