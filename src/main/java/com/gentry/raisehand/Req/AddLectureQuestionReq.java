package com.gentry.raisehand.Req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddLectureQuestionReq {
    @ApiModelProperty(value = "the id of lecture",example = "1")
    @NonNull
    private int lectureId;
    @ApiModelProperty(value = "the content of question",example = "Which of the following is true?")
    @NonNull
    private String questionContent;
    @ApiModelProperty(value = "The status of the issue, indicating that the issue is saved, sent, or expired",example = "saved")
    @NonNull
    private String questionStatus;
    @ApiModelProperty(value = "Type of question, multiple choice, single choice, text question",example = "multiple choice")
    @NonNull
    private String questionType;
}
