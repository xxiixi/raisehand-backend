package com.gentry.raisehand.Res;

import com.gentry.raisehand.entity.LectureQuestion;
import com.gentry.raisehand.entity.LectureQuestionAnswer;
import lombok.Data;

import java.util.List;

@Data
public class GetLectureQuestionSRes {
    private LectureQuestion lectureQuestion;
    private List<LectureQuestionAnswer>lectureQuestionAnswers;
}
