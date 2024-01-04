package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.CheckTeacherReq;
import com.gentry.raisehand.Req.LoginReq;
import com.gentry.raisehand.Res.LoginRes;
import com.gentry.raisehand.entity.Student;
import com.gentry.raisehand.entity.Teacher;
import com.gentry.raisehand.service.StudentService;
import com.gentry.raisehand.service.TeacherService;
import com.gentry.raisehand.util.RestResult;
import com.gentry.raisehand.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import redis.clients.jedis.Jedis;

import java.util.UUID;

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
@Api(tags = "Teacher login(student) check")
@RestController
@RequestMapping("/gentry/raisehand/teacher")
public class TeacherController {
    @Autowired
    private StudentService studentService;
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
            QueryWrapper<Student> queryWrapperStudent = new QueryWrapper<>();
            queryWrapperStudent
                    .eq("student_email",loginReq.getEmail())
                    .eq("student_password",hash(loginReq.getPassword()+"raisehand"));
            Student student=studentService.getOne(queryWrapperStudent);
            if (student == null){
                return ResultUtils.error(0,"wrong");
            }else {
                LoginRes loginRes = new LoginRes();
                loginRes.setUserId(student.getId());
                loginRes.setStatus("student");
                Jedis jedis = new Jedis("raisehand-redis.ziwzcj.clustercfg.memorydb.cn-north-1.amazonaws.com.cn", 6379);
                String tokenS=String.valueOf(hash(student.getStudentPassword() + "raisehands"+ UUID.randomUUID()));
                jedis.set(String.valueOf(student.getId()), tokenS);
                jedis.expire(String.valueOf(student.getId()), 1728000);
                loginRes.setToken(tokenS);
                return ResultUtils.success(loginRes);
            }
        }else {
            LoginRes loginRes = new LoginRes();
            loginRes.setUserId(teacher.getId());
            loginRes.setStatus("teacher");
            Jedis jedis = new Jedis("raisehand-redis.ziwzcj.clustercfg.memorydb.cn-north-1.amazonaws.com.cn", 6379);
            String token=String.valueOf(hash(teacher.getTeacherPassword() + "raisehands"+ UUID.randomUUID()));
            jedis.set(String.valueOf(teacher.getId()), token);
            jedis.expire(String.valueOf(teacher.getId()), 1728000);
            loginRes.setToken(token);
            return ResultUtils.success(loginRes);
        }
    }
    @ApiOperation("check teacher login")
    @PostMapping(value = "/checkTeacher")
    public RestResult checkTeacher(@RequestBody CheckTeacherReq checkTeacherReq){
        return ResultUtils.success(checkTeacherFunction(checkTeacherReq.getTeacherId(),checkTeacherReq.getToken()));

    }
    public int checkTeacherFunction(int teacherId,String token){
        Jedis jedis = new Jedis("raisehand-redis.ziwzcj.clustercfg.memorydb.cn-north-1.amazonaws.com.cn",6379);
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

