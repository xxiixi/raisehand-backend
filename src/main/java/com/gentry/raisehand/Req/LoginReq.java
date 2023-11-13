package com.gentry.raisehand.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {
    @NonNull
    private String email;

    @NonNull
    private String password;
}
