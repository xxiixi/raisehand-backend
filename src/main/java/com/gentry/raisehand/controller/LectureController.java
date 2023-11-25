package com.gentry.raisehand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gentry.raisehand.Req.AddCourseReq;
import com.gentry.raisehand.Req.AddLectureReq;
import com.gentry.raisehand.Req.DeleteLectureReq;
import com.gentry.raisehand.Req.GetLectureReq;
import com.gentry.raisehand.entity.Lecture;
import com.gentry.raisehand.service.LectureService;
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
@Api(tags = "lecture add delete get")
@RestController
@RequestMapping("/gentry/raisehand/lecture")
public class LectureController {
    @Autowired
    private LectureService lectureService;
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
}

