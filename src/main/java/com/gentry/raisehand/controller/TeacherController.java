package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.*;
import com.gentry.raisehand.Res.CheckOpenIdRes;
import com.gentry.raisehand.Res.LarkLoginRes;
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
import org.springframework.http.ResponseEntity;
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
    public RestResult login(@RequestBody LoginReq loginReq) {
//        System.out.println(hash(loginReq.getPassword()+"raisehand"));
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("teacher_email", loginReq.getEmail())
                .eq("teacher_password", hash(loginReq.getPassword() + "raisehand"));
        Teacher teacher = teacherService.getOne(queryWrapper);

        if (teacher == null) {
            QueryWrapper<Student> queryWrapperStudent = new QueryWrapper<>();
            queryWrapperStudent
                    .eq("student_email", loginReq.getEmail())
                    .eq("student_password", hash(loginReq.getPassword() + "raisehand"));
            Student student = studentService.getOne(queryWrapperStudent);
            if (student == null) {
                return ResultUtils.error(0, "wrong");
            } else {
                LoginRes loginRes = new LoginRes();
                loginRes.setUserId(student.getId());
                loginRes.setStatus("student");
                Jedis jedis = new Jedis("127.0.0.1", 6379);
                String tokenS = String.valueOf(hash(student.getStudentPassword() + "raisehands" + UUID.randomUUID()));
                //
                jedis.set(String.valueOf(student.getId()), tokenS);
                jedis.expire(String.valueOf(student.getId()), 1728000);
                loginRes.setToken(tokenS);
                return ResultUtils.success(loginRes);
            }
        } else {
            LoginRes loginRes = new LoginRes();
            loginRes.setUserId(teacher.getId());
            loginRes.setStatus("teacher");
            Jedis jedis = new Jedis("127.0.0.1", 6379);
            String token = String.valueOf(hash(teacher.getTeacherPassword() + "raisehands" + UUID.randomUUID()));
            jedis.set(String.valueOf(teacher.getId()), token);
            jedis.expire(String.valueOf(teacher.getId()), 1728000);
            loginRes.setToken(token);
            return ResultUtils.success(loginRes);
        }
    }

    @ApiOperation("check teacher login")
    @PostMapping(value = "/checkTeacher")
    public RestResult checkTeacher(@RequestBody CheckTeacherReq checkTeacherReq) {
        return ResultUtils.success(checkTeacherFunction(checkTeacherReq.getTeacherId(), checkTeacherReq.getToken()));

    }

    public int checkTeacherFunction(int teacherId, String token) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println(jedis.exists(String.valueOf(teacherId)));
        System.out.println(jedis.get(String.valueOf(teacherId)));
        if (!jedis.exists(String.valueOf(teacherId))) {
            return 0;
        } else {
            if (jedis.get(String.valueOf(teacherId)).equals(token)) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    // 1. search openid --> has account --> yes-> login
    //                                  --> no -> register
    // new function: weather has openid

    @ApiOperation("check openid")
    @PostMapping(value = "/checkOpenid")
    public RestResult checkOpenid(@RequestBody CheckOpenIdReq checkOpenIdReq){
        QueryWrapper<Teacher> queryWrapperTeacher = new QueryWrapper<>();
        QueryWrapper<Student> queryWrapperStudent = new QueryWrapper<>();
        // check
        queryWrapperTeacher.eq("teacher_openid", checkOpenIdReq.getOpenid());
        Teacher teacher = teacherService.getOne(queryWrapperTeacher);

        queryWrapperStudent.eq("student_openid", checkOpenIdReq.getOpenid());
        Student student = studentService.getOne(queryWrapperStudent);

        CheckOpenIdRes checkOpenIdRes = new CheckOpenIdRes();

        if ((student == null) && (teacher == null)){
            // openid doesn't exist --> is student --> register
//            larkLoginRes.setStatus("student");
            checkOpenIdRes.setHasOpenid(false);

            return ResultUtils.success(checkOpenIdRes);
        }else {
            // openid exist --> login
            if (student != null){
                checkOpenIdRes.setEmail(student.getStudentEmail());
                checkOpenIdRes.setOpenid(student.getStudentOpenid());
                checkOpenIdRes.setHasOpenid(true);
            }else{
                checkOpenIdRes.setOpenid(teacher.getTeacherOpenid());
                checkOpenIdRes.setEmail(teacher.getTeacherEmail());
                checkOpenIdRes.setHasOpenid(true);
            }
            return ResultUtils.success(checkOpenIdRes);
        }
    }

    // lark login method
    // 1. 判断学生没邮箱 --> 使用 openid --> 去student表里找 --> 没有说明没注册过 --> 前端弹出一个xxx写邮箱和密码
    // --> 继续弹窗，选择课程 （6460 6750 6001）--> 学生信息，选择的课程，插入到表course
    // 2. 学生注册过了，有邮箱 --> 正常登录
    @ApiOperation("Lark login")
    @PostMapping(value = "/larkLogin")
    public RestResult larkLogin(@RequestBody LarkLoginReq larkLoginReq) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        // check openid in teacher
        queryWrapper
                .eq("teacher_openid", larkLoginReq.getOpenid())
                .eq("teacher_userid", larkLoginReq.getUserid())
                .eq("teacher_email", larkLoginReq.getEmail());
        Teacher teacher = teacherService.getOne(queryWrapper);
        System.out.println("teacher:" + larkLoginReq.getOpenid());
        LarkLoginRes larkLoginRes = new LarkLoginRes();
        // Student Login
        if (teacher == null) {
            QueryWrapper<Student> queryWrapperStudent = new QueryWrapper<>();
            // check openid in student
            queryWrapperStudent
                    .eq("student_openid", larkLoginReq.getOpenid())
                    .eq("student_userid", larkLoginReq.getUserid())
                    .eq("student_email", larkLoginReq.getEmail());
            Student student = studentService.getOne(queryWrapperStudent);

            // Login with Lark for the first time(openid doesn't exist in db)
            // save info
            if (student == null) {
                // let students fill their info
                // response

//                larkLoginRes.setStatus("student");
                return ResultUtils.error("error");
            } else {
                // student login
                larkLoginRes.setUserId(student.getId());
                larkLoginRes.setStatus("student");
                larkLoginRes.setName(student.getStudentName());
                Jedis jedis = new Jedis("127.0.0.1", 6379);
                String tokenS = String.valueOf(hash(student.getStudentPassword() + "raisehands" + UUID.randomUUID()));
                jedis.set(String.valueOf(student.getId()), tokenS);
                jedis.expire(String.valueOf(student.getId()), 1728000);
                larkLoginRes.setToken(tokenS);
                return ResultUtils.success(larkLoginRes);
            }
        } else {
            // Teacher register
            larkLoginRes.setUserId(teacher.getId());
            larkLoginRes.setStatus("teacher");
            Jedis jedis = new Jedis("127.0.0.1", 6379);
            String token = String.valueOf(hash(teacher.getTeacherPassword() + "raisehands" + UUID.randomUUID()));
            jedis.set(String.valueOf(teacher.getId()), token);
            jedis.expire(String.valueOf(teacher.getId()), 1728000);
            larkLoginRes.setToken(token);
            return ResultUtils.success(larkLoginRes);
        }
    }


    @ApiOperation("add new student")
    @PostMapping(value = "/addNewStudent")
    public RestResult addNewStudent(@RequestBody LarkRegisterReq larkRegisterReq){
        Student newStudent = new Student();

        newStudent
                .setStudentOpenid(larkRegisterReq.getOpenid())
                .setStudentName(larkRegisterReq.getName())
                .setStudentMobile(larkRegisterReq.getMobile())
                .setStudentAvatarUrl(larkRegisterReq.getAvatar_url())
                .setStudentUserid(larkRegisterReq.getUserid())
                .setStudentEmail(larkRegisterReq.getEmail())
                .setStudentPassword(String.valueOf(hash(larkRegisterReq.getPassword() + "raisehand")));  // 加密
        studentService.save(newStudent);


        return ResultUtils.success();
    }


}

