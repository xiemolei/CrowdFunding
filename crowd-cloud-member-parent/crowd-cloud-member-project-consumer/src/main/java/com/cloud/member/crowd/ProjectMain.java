package com.cloud.member.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author : xiemogaminari
 * create at:  2020-10-30  23:35
 * @description:
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ProjectMain {
    public static void main(String[] args) {
        SpringApplication.run(ProjectMain.class, args);
    }
}