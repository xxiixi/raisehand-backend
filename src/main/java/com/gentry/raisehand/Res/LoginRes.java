package com.gentry.raisehand.Res;

import lombok.Data;

@Data
public class LoginRes {
    private Integer userId;
    private String token;
    private String status;
}
