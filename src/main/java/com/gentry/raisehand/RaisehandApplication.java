package com.gentry.raisehand;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.gentry.raisehand.mapper")
@SpringBootApplication
public class RaisehandApplication {
    public static void main(String[] args) {
        SpringApplication.run(RaisehandApplication.class, args);
    }
}
