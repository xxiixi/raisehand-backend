package com.gentry.raisehand.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @program: gentry-gtsi-raisehand
 * @author: Xi WANG
 * @create: 2024-04-10 15:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendVerificationCodeReq {
    @NonNull
    private String email;
    private String code;
}
