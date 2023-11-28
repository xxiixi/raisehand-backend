package com.gentry.raisehand.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCourseSemesterReq {
    @NonNull
    private String courseName;
    @NonNull
    private String courseCode;
    @NonNull
    private int teacherId;
}
