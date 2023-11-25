package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.AddLectureQuestionReq;
import com.gentry.raisehand.Req.DeleteLectureQuestionReq;
import com.gentry.raisehand.Req.GetLectureQuestionReq;
import com.gentry.raisehand.entity.LectureQuestion;
import com.gentry.raisehand.service.LectureQuestionService;
import com.gentry.raisehand.util.RestResult;
import com.gentry.raisehand.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lyt
 * @since 2023-11-25
 */
@Api(tags = "LectureQuestion add delete get")
@RestController
@RequestMapping("/gentry/raisehand/lecture-question")
public class LectureQuestionController {
    @Autowired
    private LectureQuestionService lectureQuestionService;
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
}

