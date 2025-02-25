package com.gentry.raisehand.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddStudentAnswerChoiceReq {
    @NonNull
    private int studentId;
    @NonNull
    private int questionId;
    @NonNull
    private int answerId;
    @NonNull
    private int lectureId;
}
