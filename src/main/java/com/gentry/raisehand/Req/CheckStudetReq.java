package com.gentry.raisehand.Req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckStudetReq {
    @ApiModelProperty(value = "the id of student",example = "1")
    @NonNull
    private int studentId;
    @ApiModelProperty(value = "the token of student",example = "/")
    @NonNull
    private String token;
}
