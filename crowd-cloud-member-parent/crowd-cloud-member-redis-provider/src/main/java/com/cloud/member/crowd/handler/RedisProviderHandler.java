package com.cloud.member.crowd.handler;

import com.mo.crowd.util.ResultEntity;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author : xiemogaminari
 * create at:  2020-10-21  23:44
 * @description:
 */
@RestController
public class RedisProviderHandler {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/set/redis/key/value/remote")
    public ResultEntity<String> setRedisKeyValueRemote(
            @RequestParam("key") String key,
            @RequestParam("value") String value
    ){
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

            operations.set(key, value);

            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    };

    @RequestMapping("/set/redis/key/value/remote/with/timeout")
    public ResultEntity<String> setsetRedisKeyValueRemoteWithTimeout(
            @RequestParam("key") String key,
            @RequestParam("value") String value,
            @RequestParam("time") long time,
            @RequestParam("timeUnix") TimeUnit timeUnix
    ){
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

            operations.set(key, value, time, timeUnix);

            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    };

    @RequestMapping("get/redis/value/by/key/remote")
    public ResultEntity<String> getRedisValueByKeyRemote(
            @RequestParam("key") String key
    ){
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

            String value = operations.get(key);

            return ResultEntity.successWithData(value);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    };

    @RequestMapping("remove/redis/value/by/key/remote")
    public ResultEntity<String> removeRedisValueByKeyRemote(
            @RequestParam("key") String key
    ){
        try {

            stringRedisTemplate.delete(key);

            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    };

}