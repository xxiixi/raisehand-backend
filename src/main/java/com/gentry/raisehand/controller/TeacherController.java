package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.LoginReq;
import com.gentry.raisehand.Res.LoginRes;
import com.gentry.raisehand.entity.Teacher;
import com.gentry.raisehand.service.TeacherService;
import com.gentry.raisehand.util.RestResult;
import com.gentry.raisehand.util.ResultUtils;
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
@RestController
@RequestMapping("/gentry/raisehand/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentController studentController;

    @PostMapping(value = "/login")
    public RestResult login(@RequestBody LoginReq loginReq){
//        System.out.println(hash(loginReq.getPassword()+"raisehand"));
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("teacher_email",loginReq.getEmail())
                .eq("teacher_password",hash(loginReq.getPassword()+"raisehand"));
        Teacher teacher=teacherService.getOne(queryWrapper);
        if (teacher == null){
            return studentController.studentLogin(loginReq);
        }else {
            LoginRes loginRes = new LoginRes();
            loginRes.setUserId(teacher.getId());
            loginRes.setStatus("student");
            Jedis jedis = new Jedis("127.0.0.1", 6379);
            jedis.set(String.valueOf(teacher.getId()), String.valueOf(hash(teacher.getTeacherPassword() + "raisehand")));
            jedis.expire(String.valueOf(teacher.getId()), 1728000);
            loginRes.setToken(String.valueOf(hash(teacher.getTeacherPassword() + "raisehand")));
            return ResultUtils.success(loginRes);
        }
    }
}

