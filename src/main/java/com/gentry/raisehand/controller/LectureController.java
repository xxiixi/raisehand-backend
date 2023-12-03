package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.*;
import com.gentry.raisehand.Res.GetParticipationRes;
import com.gentry.raisehand.Res.GetStudentParticipationRes;
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
 * @since 2023-11-25
 */
@CrossOrigin
@Api(tags = "lecture add delete get")
@RestController
@RequestMapping("/gentry/raisehand/lecture")
public class LectureController {
    @Autowired
    private LectureService lectureService;
    @Autowired
    private StudentAnswerChoiceService studentAnswerChoiceService;
    @Autowired
    private LectureQuestionAnswerService lectureQuestionAnswerService;
    @Autowired
    private StudentQuestionService studentQuestionService;
    @Autowired
    private StudentCourseService studentCourseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentFeedbackService studentFeedbackService;
    @ApiOperation("Lecture add")
    @PostMapping(value = "/addLecture")
    public RestResult addLecture(@RequestBody AddLectureReq addLectureReq){
        Lecture lecture=new Lecture();
        lecture.setCourseId(addLectureReq.getCourseId());
        lecture.setLectureName(addLectureReq.getLectureName());
        lectureService.save(lecture);
        return ResultUtils.success(lecture);
    }
    @ApiOperation("Lecture delete")
    @PostMapping(value = "/deleteLecture")
    public RestResult deleteLecture(@RequestBody DeleteLectureReq deleteLectureReq){
        TeacherController teacherController=new TeacherController();
        int loginStatus=teacherController.checkTeacherFunction(deleteLectureReq.getTeacherId(),deleteLectureReq.getToken());
        if (loginStatus == 1){
            lectureService.removeById(deleteLectureReq.getLectrueId());
        }else {
            return ResultUtils.error(loginStatus,"error");
        }
        return ResultUtils.success(lectureService.getById(deleteLectureReq.getLectrueId()));
    }
    @ApiOperation("Lecture get")
    @PostMapping(value = "/getLecture")
    public RestResult getLecture(@RequestBody GetLectureReq getLectureReq){
        QueryWrapper<Lecture> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("course_id",getLectureReq.getCourseId());
        List<Lecture>lectureList=lectureService.list(queryWrapper);
        return ResultUtils.success(lectureList);
    }
    @ApiOperation("Participation get")
    @PostMapping(value = "/getParticipation")
    public RestResult getParticipation(@RequestBody GetParticipationReq getParticipationReq){
        QueryWrapper<Lecture> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("course_id",getParticipationReq.getCourseId());
        List<Lecture>lectureList=lectureService.list(queryWrapper);
        GetParticipationRes getParticipationRes = new GetParticipationRes();
        List<String>lectures=new ArrayList<>();
        List<Integer>participation=new ArrayList<>();
        List<LectureQuestionAnswer>lectureQuestionAnswerList=new ArrayList<>();
        List<Float>accuracy=new ArrayList<>();
        for (Lecture lecture:lectureList){
            QueryWrapper<StudentAnswerChoice>studentAnswerChoiceQueryWrapper=new QueryWrapper<>();
            studentAnswerChoiceQueryWrapper
                    .eq("lecture_id",lecture.getId());
            List<StudentAnswerChoice>studentAnswerChoiceList=studentAnswerChoiceService.list(studentAnswerChoiceQueryWrapper);
            for (StudentAnswerChoice studentAnswerChoice:studentAnswerChoiceList){
                LectureQuestionAnswer lectureQuestionAnswer=lectureQuestionAnswerService.getById(studentAnswerChoice.getAnswerId());
                if(lectureQuestionAnswer.getAnswerStatus().equals("correct")){
                    lectureQuestionAnswerList.add(lectureQuestionAnswer);
                }
            }
            QueryWrapper<StudentQuestion>studentQuestionQueryWrapper=new QueryWrapper<>();
            studentQuestionQueryWrapper.eq("lecture_id",lecture.getId());
            List<StudentQuestion>studentQuestionList=studentQuestionService.list(studentQuestionQueryWrapper);
            lectures.add(lecture.getLectureName());
            participation.add(studentAnswerChoiceList.size()+studentQuestionList.size());
            if (studentAnswerChoiceList.size()==0){
                accuracy.add((float) 0);
            }else {
                accuracy.add(100*((float)lectureQuestionAnswerList.size())/((float)studentAnswerChoiceList.size()));
            }
        }
        getParticipationRes.setLectures(lectures);
        getParticipationRes.setParticipation(participation);
        getParticipationRes.setAccuracy(accuracy);
        return ResultUtils.success(getParticipationRes);
    }
    @ApiOperation("student Participation get")
    @PostMapping(value = "/getStudentParticipation")
    public RestResult getStudentParticipation(@RequestBody GetStudentParticipationReq getStudentParticipationReq){
        List<GetStudentParticipationRes>getStudentParticipationResList=new ArrayList<>();
        QueryWrapper<Lecture> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("course_id",getStudentParticipationReq.getCourseId());
        List<Lecture>lectureList=lectureService.list(queryWrapper);
        QueryWrapper<StudentCourse>studentCourseQueryWrapper=new QueryWrapper<>();
        studentCourseQueryWrapper.eq("course_id",getStudentParticipationReq.getCourseId());
        List<StudentCourse>studentCourseList=studentCourseService.list(studentCourseQueryWrapper);
        for (StudentCourse studentCourse:studentCourseList){
            GetStudentParticipationRes getStudentParticipationRes=new GetStudentParticipationRes();
            Student student=studentService.getById(studentCourse.getStudentId());
            getStudentParticipationRes.setStudentName(student.getStudentName());
            getStudentParticipationRes.setStudentId(student.getId());
            QueryWrapper<StudentFeedback>studentFeedbackQueryWrapper=new QueryWrapper<>();
            studentFeedbackQueryWrapper
                    .eq("course_id",getStudentParticipationReq.getCourseId())
                    .eq("student_id",student.getId());
            List<StudentFeedback> studentFeedbackList=studentFeedbackService.list(studentFeedbackQueryWrapper);
            float responseCount =0;
            float correct=0;
            int attendance=0;
            int questionNum=0;
            for (Lecture lecture:lectureList){
                QueryWrapper<StudentQuestion>studentQuestionQueryWrapper=new QueryWrapper<>();
                studentQuestionQueryWrapper
                        .eq("lecture_id",lecture.getId())
                        .eq("student_id",student.getId());
                List<StudentQuestion>studentQuestionList=studentQuestionService.list(studentQuestionQueryWrapper);
                questionNum+=studentQuestionList.size();
                QueryWrapper<StudentAnswerChoice>studentAnswerChoiceQueryWrapper=new QueryWrapper<>();
                studentAnswerChoiceQueryWrapper
                        .eq("lecture_id",lecture.getId())
                        .eq("student_id",student.getId());
                List<StudentAnswerChoice>studentAnswerChoiceList=studentAnswerChoiceService.list(studentAnswerChoiceQueryWrapper);
                if (studentAnswerChoiceList.size()!=0){
                    attendance+=1;
                }
                for (StudentAnswerChoice studentAnswerChoice:studentAnswerChoiceList){
                    LectureQuestionAnswer lectureQuestionAnswer=lectureQuestionAnswerService.getById(studentAnswerChoice.getAnswerId());
                    if(lectureQuestionAnswer.getAnswerStatus().equals("correct")){
                        correct+=1;
                    }
                }
                responseCount+=studentAnswerChoiceList.size();

            }
            getStudentParticipationRes.setResponseCount((int) responseCount);
            if(responseCount!=0){
                getStudentParticipationRes.setResponseAccuracy(100*correct/responseCount);
            }else {
                getStudentParticipationRes.setResponseAccuracy((float) 0);
            }
            getStudentParticipationRes.setAttendance(attendance);
            getStudentParticipationRes.setAskQuestionNum(questionNum);
            getStudentParticipationRes.setFeedbackGivedNum(studentFeedbackList.size());
            getStudentParticipationResList.add(getStudentParticipationRes);
        }
        return ResultUtils.success(getStudentParticipationResList);
    }
}

