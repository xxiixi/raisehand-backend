package com.gentry.raisehand.Req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddStudentFeedbackReq {
    @NonNull
    private int studentId;
    @NonNull
    private int courseId;
    @NonNull
    private String feedbackContent;
    @ApiModelProperty(value = "The status of feedback, Like anonymity",example = "anonymity")
    @NonNull
    private String feedbackStatus;
}
