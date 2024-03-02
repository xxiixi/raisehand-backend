package com.gentry.raisehand.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @program: gentry-gtsi-raisehand
 * @author: Xi WANG
 * @create: 2024-03-01 15:40
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckOpenIdReq {
    @NonNull
    private String openid;
}
