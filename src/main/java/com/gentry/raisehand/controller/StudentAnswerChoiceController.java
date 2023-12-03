package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.AddStudentAnswerChoiceReq;
import com.gentry.raisehand.Req.AddStudentQuestionReq;
import com.gentry.raisehand.entity.StudentAnswerChoice;
import com.gentry.raisehand.entity.StudentQuestion;
import com.gentry.raisehand.service.StudentAnswerChoiceService;
import com.gentry.raisehand.util.RestResult;
import com.gentry.raisehand.util.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lyt
 * @since 2023-11-26
 */
@CrossOrigin
@RestController
@RequestMapping("/gentry/raisehand/student-answer-choice")
public class StudentAnswerChoiceController {
    @Autowired
    private StudentAnswerChoiceService studentAnswerChoiceService;
    @ApiOperation("StudentAnswerChoice add")
    @PostMapping(value = "/addStudentAnswerChoice")
    public RestResult addStudentAnswerChoice(@RequestBody AddStudentAnswerChoiceReq addStudentAnswerChoiceReq){

        QueryWrapper<StudentAnswerChoice>studentAnswerChoiceQueryWrapper=new QueryWrapper<>();
        studentAnswerChoiceQueryWrapper
                .eq("question_id",addStudentAnswerChoiceReq.getQuestionId())
                .eq("student_id",addStudentAnswerChoiceReq.getStudentId());
        StudentAnswerChoice studentAnswerChoice =studentAnswerChoiceService.getOne(studentAnswerChoiceQueryWrapper);
        if(studentAnswerChoice != null){
            studentAnswerChoice.setAnswerId(addStudentAnswerChoiceReq.getAnswerId());
            studentAnswerChoiceService.update(studentAnswerChoice,studentAnswerChoiceQueryWrapper);
        }else {
            studentAnswerChoice=new StudentAnswerChoice();
            studentAnswerChoice
                .setAnswerId(addStudentAnswerChoiceReq.getAnswerId())
                .setQuestionId(addStudentAnswerChoiceReq.getQuestionId())
                .setStudentId(addStudentAnswerChoiceReq.getStudentId())
                .setLectureId(addStudentAnswerChoiceReq.getLectureId());
            studentAnswerChoiceService.save(studentAnswerChoice);
        }

        return ResultUtils.success(studentAnswerChoice);
    }
}

