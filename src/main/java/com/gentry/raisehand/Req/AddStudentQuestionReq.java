package com.gentry.raisehand.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddStudentQuestionReq {
    @NonNull
    private int studentId;
    @NonNull
    private int lectureId;
    @NonNull
    private String studentQuestionContent;
    @NonNull
    private String studentQuestionStatus;
    @NonNull
    private String studentQuestionType;
}
