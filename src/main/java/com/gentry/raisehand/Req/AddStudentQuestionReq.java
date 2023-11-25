package com.gentry.raisehand.Req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddStudentQuestionReq {
    @ApiModelProperty(value = "the id of student",example = "1")
    @NonNull
    private int studentId;
    @ApiModelProperty(value = "the id of lecture",example = "1")
    @NonNull
    private int lectureId;
    @ApiModelProperty(value = "the content of studentQuestion",example = "Teacher, how many noses does an elephant have?")
    @NonNull
    private String studentQuestionContent;
    @ApiModelProperty(value = "The status of the problem, whether it has been solved or not",example = "solced")
    @NonNull
    private String studentQuestionStatus;
    @ApiModelProperty(value = "The type of question, not currently used, you need to give it a value, text",example = "text")
    @NonNull
    private String studentQuestionType;
}
