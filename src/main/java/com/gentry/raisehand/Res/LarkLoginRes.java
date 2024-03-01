package com.gentry.raisehand.Res;

import lombok.Data;

/**
 * @program: gentry-gtsi-raisehand
 * @author: Xi WANG
 * @create: 2024-01-25 02:53
 **/
@Data
public class LarkLoginRes {
    private Integer userId;
    private String token;
    private String status;
    private String name;
}
