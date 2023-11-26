package com.gentry.raisehand.controller;


import com.gentry.raisehand.Req.GetStudentQuestionReq;
import com.gentry.raisehand.Req.ResponseStudentQuestionReq;
import com.gentry.raisehand.entity.TeacherStudentQuestion;
import com.gentry.raisehand.service.TeacherStudentQuestionService;
import com.gentry.raisehand.util.RestResult;
import com.gentry.raisehand.util.ResultUtils;
import io.swagger.annotations.Api;
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
@Api(tags = "Teacher response StudentQuestion")
@RestController
@RequestMapping("/gentry/raisehand/teacher-student-question")
public class TeacherStudentQuestionController {
    @Autowired
    private TeacherStudentQuestionService teacherStudentQuestionService;
    @ApiOperation("Teacher response StudentQuestion")
    @PostMapping(value = "/responseStudentQuestion")
    public RestResult responseStudentQuestion(@RequestBody ResponseStudentQuestionReq responseStudentQuestionReq){
        TeacherStudentQuestion teacherStudentQuestion =new TeacherStudentQuestion();
        teacherStudentQuestion
                .setQuestionId(responseStudentQuestionReq.getQuestionId())
                .setTeacherId(responseStudentQuestionReq.getTeacherId())
                .setTsContent(responseStudentQuestionReq.getTsContent());
        teacherStudentQuestionService.save(teacherStudentQuestion);
        return ResultUtils.success(teacherStudentQuestion);

    }

}

