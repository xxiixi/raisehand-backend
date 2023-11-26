package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.gentry.raisehand.Req.GetLectureQuestionSReq;
import com.gentry.raisehand.Req.PostLectureQuestionReq;
import com.gentry.raisehand.Res.GetLectureQuestionSRes;
import com.gentry.raisehand.entity.LectureQuestion;
import com.gentry.raisehand.entity.LectureQuestionAnswer;
import com.gentry.raisehand.entity.Student;
import com.gentry.raisehand.entity.TeacherStudentQuestion;
import com.gentry.raisehand.service.LectureQuestionAnswerService;
import com.gentry.raisehand.service.LectureQuestionService;
import com.gentry.raisehand.service.TeacherStudentQuestionService;
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
    @ApiOperation("LectureQuestion get by student")
    @PostMapping(value = "/getLectureQuestionS")
    public RestResult getLectureQuestionS(@RequestBody GetLectureQuestionSReq getLectureQuestionSReq){
        QueryWrapper<TeacherStudentQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",getLectureQuestionSReq.getStudentId());
        List<TeacherStudentQuestion>teacherStudentQuestionList=teacherStudentQuestionService.list(queryWrapper);
        List<LectureQuestion>lectureQuestionList=new ArrayList<>();
        List<LectureQuestionAnswer>lectureQuestionAnswerList=new ArrayList<>();
        for(TeacherStudentQuestion teacherStudentQuestion:teacherStudentQuestionList){
            lectureQuestionList.add(lectureQuestionService.getById(teacherStudentQuestion.getQuestionId()));
            for (LectureQuestion lectureQuestion:lectureQuestionList){
                QueryWrapper<LectureQuestionAnswer> queryWrapperAnswer = new QueryWrapper<>();
                queryWrapper.eq("question_id",lectureQuestion.getId());
                lectureQuestionAnswerList.addAll(lectureQuestionAnswerService.list(queryWrapperAnswer));
            }
        }
        GetLectureQuestionSRes getLectureQuestionSRes=new GetLectureQuestionSRes();
        getLectureQuestionSRes.setLectureQuestionAnswers(lectureQuestionAnswerList);
        getLectureQuestionSRes.setLectureQuestions(lectureQuestionList);
        return ResultUtils.success(getLectureQuestionSRes);

    }

}

