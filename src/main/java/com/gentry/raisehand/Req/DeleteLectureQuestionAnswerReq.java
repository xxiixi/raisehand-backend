package com.gentry.raisehand.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DeleteLectureQuestionAnswerReq {
    @NonNull
    private int teacherId;
    @NonNull
    private int lectureQuestionAnswerId;
    @NonNull
    private String Token;
}
