package com.gentry.raisehand.Req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCourseReq {
    @ApiModelProperty(value = "the name of course",example = "Human-Computer Interaction")
    @NonNull
    private String courseName;
    @ApiModelProperty(value = "the code of course",example = "6750")
    @NonNull
    private String courseCode;
    @ApiModelProperty(value = "the semster of course",example = "FA23")
    @NonNull
    private String courseSemster;
    @ApiModelProperty(value = "the id of teacher",example = "1")
    @NonNull
    private int teacherId;
}
