package com.gentry.raisehand.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteLectureReq {
    @NonNull
    private int teacherId;
    @NonNull
    private int lectrueId;
    @NonNull
    private String Token;
}
