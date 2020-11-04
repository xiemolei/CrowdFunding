package com.mo.crowd.test;

import com.mo.crowd.util.CrowdUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author : xiemogaminari
 * create at:  2020-10-07  16:14
 * @description:
 */
public class StringTest {
    public BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void test(){
        String psw = "123123";
        String psw_md5 = CrowdUtil.md5(psw);
        System.out.printf(psw_md5);
    }

    @Test
    public void getPasswordEncoder(){
        String encoder = passwordEncoder.encode("123123");
        System.out.println(encoder);
    }
}