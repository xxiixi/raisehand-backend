package com.gentry.raisehand.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @program: gentry-gtsi-raisehand
 * @author: Xi WANG
 * @create: 2024-03-01 16:05
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LarkRegisterReq {
    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String openid;
    private String avatar_url;
    private String name;
    private String mobile;
    private String userid;
}
