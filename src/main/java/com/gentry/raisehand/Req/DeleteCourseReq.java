package com.gentry.raisehand.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCourseReq {
    @NonNull
    private int teacherId;
    @NonNull
    private int courseId;
    @NonNull
    private String Token;
}
