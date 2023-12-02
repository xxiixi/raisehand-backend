package com.gentry.raisehand.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStudentQuestionSReq {
    @NonNull
    private int lectureId;
    @NonNull
    private int studentId;
}
