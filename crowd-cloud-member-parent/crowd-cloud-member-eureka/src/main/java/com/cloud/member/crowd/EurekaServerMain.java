package com.cloud.member.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author : xiemogaminari
 * create at:  2020-10-21  14:21
 * @description:
 */

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerMain {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerMain.class, args);
    }
}