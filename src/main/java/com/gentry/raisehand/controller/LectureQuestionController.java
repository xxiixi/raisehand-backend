package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.*;
import com.gentry.raisehand.entity.LectureQuestion;
import com.gentry.raisehand.entity.PushLectureQuestion;
import com.gentry.raisehand.entity.Student;
import com.gentry.raisehand.entity.StudentCourse;
import com.gentry.raisehand.service.LectureQuestionService;
import com.gentry.raisehand.service.PushLectureQuestionService;
import com.gentry.raisehand.service.StudentCourseService;
import com.gentry.raisehand.service.StudentService;
import com.gentry.raisehand.util.RestResult;
import com.gentry.raisehand.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "LectureQuestion add delete get post move")
@RestController
@RequestMapping("/gentry/raisehand/lecture-question")
public class LectureQuestionController {
    @Autowired
    private LectureQuestionService lectureQuestionService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentCourseService studentCourseService;
    @Autowired
    private PushLectureQuestionService pushLectureQuestionService;
    @ApiOperation("LectureQuestion add")
    @PostMapping(value = "/addLectureQuestion")
    public RestResult addLectureQuestion(@RequestBody AddLectureQuestionReq addLectureQuestionReq){
        LectureQuestion lectureQuestion=new LectureQuestion();
        lectureQuestion
                .setLectureId(addLectureQuestionReq.getLectureId())
                .setQuestionContent(addLectureQuestionReq.getQuestionContent())
                .setQuestionStatus(addLectureQuestionReq.getQuestionStatus())
                .setQuestionType(addLectureQuestionReq.getQuestionType());
        lectureQuestionService.save(lectureQuestion);
        return ResultUtils.success(lectureQuestion);
    }
    @ApiOperation("LectureQuestion delete")
    @PostMapping(value = "/deleteLectureQuestion")
    public RestResult deleteLectureQuestion(@RequestBody DeleteLectureQuestionReq deleteLectureQuestionReq){
        TeacherController teacherController=new TeacherController();
        int loginStatus=teacherController.checkTeacherFunction(deleteLectureQuestionReq.getTeacherId(),deleteLectureQuestionReq.getToken());
        if (loginStatus == 1){
            lectureQuestionService.removeById(deleteLectureQuestionReq.getLectureQuestionId());
        }else {
            return ResultUtils.error(loginStatus,"error");
        }
        return ResultUtils.success(lectureQuestionService.getById(deleteLectureQuestionReq.getLectureQuestionId()));
    }
    @ApiOperation("LectureQuestion get")
    @PostMapping(value = "/getLectureQuestion")
    public RestResult getLectureQuestion(@RequestBody GetLectureQuestionReq getLectureQuestionReq){
        QueryWrapper<LectureQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("lecture_id",getLectureQuestionReq.getLectureId());
        List<LectureQuestion> lectureQuestionList=lectureQuestionService.list(queryWrapper);
        return ResultUtils.success(lectureQuestionList);
    }
    @ApiOperation("LectureQuestion post")
    @PostMapping(value = "/postLectureQuestion")
    public RestResult postLectureQuestion(@RequestBody PostLectureQuestionReq postLectureQuestionReq){
        LectureQuestion lectureQuestion=lectureQuestionService.getById(postLectureQuestionReq.getQuestionId());
        lectureQuestion.setQuestionStatus("post");
        QueryWrapper<LectureQuestion> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper
                .eq("id",postLectureQuestionReq.getQuestionId());
        lectureQuestionService.update(lectureQuestion,questionQueryWrapper);

        PushLectureQuestion pushLectureQuestion =new PushLectureQuestion();
        QueryWrapper<StudentCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",postLectureQuestionReq.getCourseId());
        List<StudentCourse>studentCourseList=studentCourseService.list(queryWrapper);
        pushLectureQuestion.setQuestionId(lectureQuestion.getId());
        List<PushLectureQuestion>pushLectureQuestionList=new ArrayList<>();
        for(StudentCourse studentCourse : studentCourseList){
            pushLectureQuestion.setLectureId(postLectureQuestionReq.getLectureId());
            pushLectureQuestion.setStudentId(studentCourse.getStudentId());
            pushLectureQuestionList.add(pushLectureQuestion);
        }
        pushLectureQuestionService.saveBatch(pushLectureQuestionList);
        return ResultUtils.success(pushLectureQuestionList);
    }
    @ApiOperation("LectureQuestion move to class")
    @PostMapping(value = "/moveLectureQuestion")
    public RestResult moveLectureQuestion(@RequestBody MoveLectureQuestionReq moveLectureQuestionReq){
        LectureQuestion lectureQuestion=lectureQuestionService.getById(moveLectureQuestionReq.getQuestionId());
        lectureQuestion.setQuestionStatus("inClass");
        QueryWrapper<LectureQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("id",moveLectureQuestionReq.getQuestionId());
        lectureQuestionService.update(lectureQuestion,queryWrapper);
        return ResultUtils.success(lectureQuestion);
    }
    @ApiOperation("LectureQuestion expire to class")
    @PostMapping(value = "/expireLectureQuestion")
    public RestResult expireLectureQuestion(@RequestBody MoveLectureQuestionReq moveLectureQuestionReq){
        LectureQuestion lectureQuestion=lectureQuestionService.getById(moveLectureQuestionReq.getQuestionId());
        lectureQuestion.setQuestionStatus("expire");
        QueryWrapper<LectureQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("id",moveLectureQuestionReq.getQuestionId());
        lectureQuestionService.update(lectureQuestion,queryWrapper);
        return ResultUtils.success(lectureQuestion);
    }
}

