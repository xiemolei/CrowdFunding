package com.mo.crowd.test;

import com.mo.crowd.util.CrowdUtil;
import org.junit.Test;

/**
 * @author : xiemogaminari
 * create at:  2020-10-07  16:14
 * @description:
 */
public class StringTest {
    @Test
    public void test(){
        String psw = "123123";
        String psw_md5 = CrowdUtil.md5(psw);
        System.out.printf(psw_md5);
    }
}