package com.gentry.raisehand.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLectureQuestionReq {
    @NonNull
    private int questionId;
    @NonNull
    private int courseId;
}
