package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.CheckTeacherReq;
import com.gentry.raisehand.Req.LoginReq;
import com.gentry.raisehand.Res.LoginRes;
import com.gentry.raisehand.entity.Teacher;
import com.gentry.raisehand.service.TeacherService;
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
@Api(tags = "Teacher login(student) check")
@RestController
@RequestMapping("/gentry/raisehand/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @ApiOperation("teacher and student login")
    @PostMapping(value = "/login")
    public RestResult login(@RequestBody LoginReq loginReq){
//        System.out.println(hash(loginReq.getPassword()+"raisehand"));
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("teacher_email",loginReq.getEmail())
                .eq("teacher_password",hash(loginReq.getPassword()+"raisehand"));
        Teacher teacher=teacherService.getOne(queryWrapper);
        if (teacher == null){
            StudentController studentController=new StudentController();
            return studentController.studentLogin(loginReq);
        }else {
            LoginRes loginRes = new LoginRes();
            loginRes.setUserId(teacher.getId());
            loginRes.setStatus("teacher");
            Jedis jedis = new Jedis("127.0.0.1", 6379);
            jedis.set(String.valueOf(teacher.getId()), String.valueOf(hash(teacher.getTeacherPassword() + "raisehand")));
            jedis.expire(String.valueOf(teacher.getId()), 1728000);
            loginRes.setToken(String.valueOf(hash(teacher.getTeacherPassword() + "raisehand")));
            return ResultUtils.success(loginRes);
        }
    }
    @ApiOperation("check teacher login")
    @PostMapping(value = "/checkTeacher")
    public RestResult checkTeacher(@RequestBody CheckTeacherReq checkTeacherReq){
        return ResultUtils.success(checkTeacherFunction(checkTeacherReq.getTeacherId(),checkTeacherReq.getToken()));

    }
    public int checkTeacherFunction(int teacherId,String token){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        System.out.println(jedis.exists(String.valueOf(teacherId)));
        System.out.println(jedis.get(String.valueOf(teacherId)));
        if (!jedis.exists(String.valueOf(teacherId))){
            return 0;
        }else {
            if (jedis.get(String.valueOf(teacherId)).equals(token)){
                return 1;
            }else{
                return 0;
            }
        }
    }
}

