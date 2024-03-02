package com.gentry.raisehand.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @program: gentry-gtsi-raisehand
 * @author: Xi WANG
 * @create: 2024-01-25 02:57
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LarkLoginReq {
    @NonNull
    private String email;

    @NonNull
    private String openid;

    @NonNull
    private String userid;
}
