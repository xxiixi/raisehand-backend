package com.gentry.raisehand.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddLectureQuestionAnswerReq {
    @NonNull
    private int questionId;
    @NonNull
    private String answerContent;
    @NonNull
    private String answerStatus;
}
