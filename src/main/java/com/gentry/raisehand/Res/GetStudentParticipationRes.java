package com.gentry.raisehand.Res;

import lombok.Data;

@Data
public class GetStudentParticipationRes {
    private int studentId;
    private String studentName;
    private int attendance;
    private int responseCount;
    private Float responseAccuracy;
    private int askQuestionNum;
    private int feedbackGivedNum;
}
