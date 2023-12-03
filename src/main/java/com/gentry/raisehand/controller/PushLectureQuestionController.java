package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.gentry.raisehand.Req.GetLectureQuestionSReq;
import com.gentry.raisehand.Req.PostLectureQuestionReq;
import com.gentry.raisehand.Res.GetLectureQuestionSRes;
import com.gentry.raisehand.entity.*;
import com.gentry.raisehand.service.*;
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
 * @since 2023-11-26
 */
@CrossOrigin
@Api(tags = "LectureQuestion push method and LectureQuestion get by student")
@RestController
@RequestMapping("/gentry/raisehand/push-lecture-question")
public class PushLectureQuestionController {
    @Autowired
    private TeacherStudentQuestionService teacherStudentQuestionService;
    @Autowired
    private LectureQuestionService lectureQuestionService;
    @Autowired
    private LectureQuestionAnswerService lectureQuestionAnswerService;
    @Autowired
    private PushLectureQuestionService pushLectureQuestionService;
    @Autowired
    private StudentAnswerChoiceService studentAnswerChoiceService;
    @ApiOperation("LectureQuestion get by student")
    @PostMapping(value = "/getLectureQuestionS")
    public RestResult getLectureQuestionS(@RequestBody GetLectureQuestionSReq getLectureQuestionSReq){
        QueryWrapper<PushLectureQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("lecture_id",getLectureQuestionSReq.getLectureId())
                .eq("student_id",getLectureQuestionSReq.getStudentId());
        List<PushLectureQuestion>pushLectureQuestionList=pushLectureQuestionService.list(queryWrapper);
        List<GetLectureQuestionSRes>getLectureQuestionSResList=new ArrayList<>();
        for(PushLectureQuestion pushLectureQuestion:pushLectureQuestionList){
            GetLectureQuestionSRes getLectureQuestionSRes=new GetLectureQuestionSRes();
            LectureQuestion lectureQuestion =lectureQuestionService.getById(pushLectureQuestion.getQuestionId());
            QueryWrapper<LectureQuestionAnswer> queryWrapperAnswer = new QueryWrapper<>();
            queryWrapperAnswer
                    .eq("question_id",lectureQuestion.getId());
            List<LectureQuestionAnswer> lectureQuestionAnswerList = lectureQuestionAnswerService.list(queryWrapperAnswer);
            for(LectureQuestionAnswer lectureQuestionAnswer:lectureQuestionAnswerList){
                QueryWrapper<StudentAnswerChoice> studentAnswerChoiceQueryWrapper=new QueryWrapper<>();
                studentAnswerChoiceQueryWrapper
                        .eq("question_id",lectureQuestionAnswer.getQuestionId())
                        .eq("answer_id",lectureQuestionAnswer.getId())
                        .eq("student_id",getLectureQuestionSReq.getStudentId());
                StudentAnswerChoice studentAnswerChoice = studentAnswerChoiceService.getOne(studentAnswerChoiceQueryWrapper);
                if (studentAnswerChoice != null){
                    lectureQuestionAnswer.setAnswerStatus("choose");
                }else {
                    lectureQuestionAnswer.setAnswerStatus("notchoose");
                }

            }
            getLectureQuestionSRes.setLectureQuestionAnswers(lectureQuestionAnswerList);
            getLectureQuestionSRes.setLectureQuestion(lectureQuestion);
            getLectureQuestionSResList.add(getLectureQuestionSRes);
        }


        return ResultUtils.success(getLectureQuestionSResList);

    }

}

