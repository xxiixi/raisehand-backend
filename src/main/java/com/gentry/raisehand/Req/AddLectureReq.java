package com.gentry.raisehand.Req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddLectureReq {
    @ApiModelProperty(value = "the id of course",example = "1")
    @NonNull
    private int courseId;
    @ApiModelProperty(value = "the name of lecture",example = "Human-Computer Interaction")
    @NonNull
    private String lectureName;
}
