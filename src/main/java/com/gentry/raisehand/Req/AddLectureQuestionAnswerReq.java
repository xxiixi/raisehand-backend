package com.gentry.raisehand.Req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddLectureQuestionAnswerReq {
    @ApiModelProperty(value = "the id of question",example = "1")
    @NonNull
    private int questionId;
    @ApiModelProperty(value = "the content of answer",example = "An elephant has no trunk.")
    @NonNull
    private String answerContent;
    @ApiModelProperty(value = "the status of answer,There is no answer to the text question, one of the single choice states is correct, multiple choice states are correct, but at least one is correct",example = "correct")
    @NonNull
    private String answerStatus;
}
