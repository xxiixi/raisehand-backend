package com.gentry.raisehand.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddLectureQuestionReq {
    @NonNull
    private int lectureId;
    @NonNull
    private String questionContent;
    @NonNull
    private String questionStatus;
    @NonNull
    private String questionType;
}
