package com.gentry.raisehand.Res;

import lombok.Data;

import java.util.List;

@Data
public class GetParticipationRes {
    List<String> lectures;
    List<Integer> participation;
    List<Float> Accuracy;
}
