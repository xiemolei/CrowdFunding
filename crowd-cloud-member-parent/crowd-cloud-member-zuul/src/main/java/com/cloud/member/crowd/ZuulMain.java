package com.cloud.member.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author : xiemogaminari
 * create at:  2020-10-22  00:39
 * @description:
 */
@EnableZuulProxy
@SpringBootApplication
public class ZuulMain {
    public static void main(String[] args) {
        SpringApplication.run(ZuulMain.class, args);
    }
}