package com.gentry.raisehand.Res;

import lombok.Data;

/**
 * @program: gentry-gtsi-raisehand
 * @author: Xi WANG
 * @create: 2024-03-01 15:42
 **/
@Data
public class CheckOpenIdRes {
    private String openid;
    private String email;
    private Boolean hasOpenid;
}
