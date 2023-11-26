package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.AddLectureQuestionAnswerReq;
import com.gentry.raisehand.Req.DeleteLectureQuestionAnswerReq;
import com.gentry.raisehand.Req.GetLectureQuestionAnswerReq;
import com.gentry.raisehand.entity.LectureQuestionAnswer;
import com.gentry.raisehand.service.LectureQuestionAnswerService;
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
 * @since 2023-11-25
 */
@CrossOrigin
@Api(tags = "LectureQuestionAnswer add delete get")
@RestController
@RequestMapping("/gentry/raisehand/lecture-question-answer")
public class LectureQuestionAnswerController {
    @Autowired
    private LectureQuestionAnswerService lectureQuestionAnswerService;
    @ApiOperation("LectureQuestionAnswer add")
    @PostMapping(value = "/addLectureQuestionAnswer")
    public RestResult addLectureQuestionAnswer(@RequestBody AddLectureQuestionAnswerReq addLectureQuestionAnswerReq){
        LectureQuestionAnswer letureQuestionAnswer=new LectureQuestionAnswer();
        letureQuestionAnswer
                .setQuestionId(addLectureQuestionAnswerReq.getQuestionId())
                .setAnswerContent(addLectureQuestionAnswerReq.getAnswerContent())
                .setAnswerRank(addLectureQuestionAnswerReq.getAnswerRank())
                .setAnswerStatus(addLectureQuestionAnswerReq.getAnswerStatus());
        lectureQuestionAnswerService.save(letureQuestionAnswer);
        return ResultUtils.success(letureQuestionAnswer);
    }
    @ApiOperation("LectureQuestionAnswer delete")
    @PostMapping(value = "/deleteLectureQuestionAnswer")
    public RestResult deleteLectureQuestionAnswer(@RequestBody DeleteLectureQuestionAnswerReq deleteLectureQuestionAnswerReq){
        TeacherController teacherController=new TeacherController();
        int loginStatus=teacherController.checkTeacherFunction(deleteLectureQuestionAnswerReq.getTeacherId(),deleteLectureQuestionAnswerReq.getToken());
        if (loginStatus == 1){
            lectureQuestionAnswerService.removeById(deleteLectureQuestionAnswerReq.getLectureQuestionAnswerId());
        }else {
            return ResultUtils.error(loginStatus,"error");
        }
        return ResultUtils.success(lectureQuestionAnswerService.getById(deleteLectureQuestionAnswerReq.getLectureQuestionAnswerId()));
    }
    @ApiOperation("LectureQuestionAnswer get")
    @PostMapping(value = "/getLectureQuestionAnswer")
    public RestResult getLectureQuestionAnswer(@RequestBody GetLectureQuestionAnswerReq getLectureQuestionAnswerReq){
        QueryWrapper<LectureQuestionAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("question_id",getLectureQuestionAnswerReq.getQuestionId());
        List<LectureQuestionAnswer> lectureQuestionAnswerList=lectureQuestionAnswerService.list(queryWrapper);
        return ResultUtils.success(lectureQuestionAnswerList);
    }
}

