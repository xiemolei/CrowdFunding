package com.cloud.member.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : xiemogaminari
 * create at:  2020-10-21  14:53
 * @description:
 */
@MapperScan("com.cloud.member.crowd.mapper")
@SpringBootApplication
public class MysqlProviderMain {
    public static void main(String[] args) {
        SpringApplication.run(MysqlProviderMain.class, args);
    }
}