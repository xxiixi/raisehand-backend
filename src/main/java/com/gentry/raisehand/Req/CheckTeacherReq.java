package com.gentry.raisehand.Req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckTeacherReq {
    @ApiModelProperty(value = "the id of teacher",example = "1")
    @NonNull
    private int teacherId;
    @ApiModelProperty(value = "the token of teacher",example = "/")
    @NonNull
    private String token;
}
