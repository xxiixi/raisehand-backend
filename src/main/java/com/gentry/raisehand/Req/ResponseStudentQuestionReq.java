package com.gentry.raisehand.Req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStudentQuestionReq {
    @NonNull
    private int teacherId;
    @NonNull
    private int questionId;
    @ApiModelProperty(value = "the Content of teacher give to student question",example = "That's a good question. Neither would I")
    @NonNull
    private String tsContent;

}
