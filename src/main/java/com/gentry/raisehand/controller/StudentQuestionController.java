package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.AddStudentQuestionReq;
import com.gentry.raisehand.Req.GetStudentQuestionReq;
import com.gentry.raisehand.entity.LectureQuestionAnswer;
import com.gentry.raisehand.entity.StudentQuestion;
import com.gentry.raisehand.service.StudentQuestionService;
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
@Api(tags = "StudentQuestion add get")
@RestController
@RequestMapping("/gentry/raisehand/student-question")
public class StudentQuestionController {
    @Autowired
    private StudentQuestionService studentQuestionService;
    @ApiOperation("StudentQuestion add")
    @PostMapping(value = "/addStudentQuestion")
    public RestResult addStudentQuestion(@RequestBody AddStudentQuestionReq addStudentQuestionReq){
        StudentQuestion studentQuestion=new StudentQuestion();
        studentQuestion
                .setLectureId(addStudentQuestionReq.getLectureId())
                .setStudentId(addStudentQuestionReq.getStudentId())
                .setStudentQuestionContent(addStudentQuestionReq.getStudentQuestionContent())
                .setStudentQuestionStatus(addStudentQuestionReq.getStudentQuestionStatus())
                .setStudentQuestionType(addStudentQuestionReq.getStudentQuestionType());
        studentQuestionService.save(studentQuestion);
        return ResultUtils.success(studentQuestion);
    }
    @ApiOperation("StudentQuestion get")
    @PostMapping(value = "/getStudentQuestion")
    public RestResult getStudentQuestion(@RequestBody GetStudentQuestionReq getStudentQuestionReq){
        QueryWrapper<StudentQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("lecture_id",getStudentQuestionReq.getLectureId());
        List<StudentQuestion> studentQuestionList=studentQuestionService.list(queryWrapper);
        return ResultUtils.success(studentQuestionList);
    }
}

