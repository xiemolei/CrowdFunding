package com.cloud.member.crowd.api;

import com.mo.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

/**
 * @author : xiemogaminari
 * create at:  2020-10-21  23:29
 * @description:
 */
@FeignClient("crowd-redis")
public interface RedisRemoteService {
    @RequestMapping("/set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(
            @RequestParam("key") String key,
            @RequestParam("value") String value
    );

    @RequestMapping("/set/redis/key/value/remote/with/timeout")
    ResultEntity<String> setsetRedisKeyValueRemoteWithTimeout(
            @RequestParam("key") String key,
            @RequestParam("value") String value,
            @RequestParam("time") long time,
            @RequestParam("timeUnix") TimeUnit timeUnix
    );

    @RequestMapping("get/redis/value/by/key/remote")
    ResultEntity<String> getRedisValueByKeyRemote(
            @RequestParam("key") String key
    );

    @RequestMapping("remove/redis/value/by/key/remote")
    ResultEntity<String> removeRedisValueByKeyRemote(
            @RequestParam("key") String key
    );
}