package com.gentry.raisehand.Res;

import com.gentry.raisehand.entity.LectureQuestion;
import com.gentry.raisehand.entity.LectureQuestionAnswer;
import lombok.Data;

import java.util.List;

@Data
public class GetLectureQuestionSRes {
    private List<LectureQuestion>lectureQuestions;
    private List<LectureQuestionAnswer>lectureQuestionAnswers;
}
