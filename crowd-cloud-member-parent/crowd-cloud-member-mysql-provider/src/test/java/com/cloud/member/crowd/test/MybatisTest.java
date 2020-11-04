package com.cloud.member.crowd.test;

import com.cloud.member.crowd.entity.po.MemberPO;
import com.cloud.member.crowd.mapper.MemberPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * @author : xiemogaminari
 * create at:  2020-10-21  15:25
 * @description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTest {
    @Resource
    private DataSource dataSource;

    // @Resource 是ByName 是java原生注解，低耦合推荐使用 ，@Autowrite 是ByType 是Spring注解
    @Resource
    private MemberPOMapper memberPOMapper;

    Logger logger = LoggerFactory.getLogger(MybatisTest.class);

    @Test
    public void testMapper() {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String source = "123123";

        String encode = passwordEncoder.encode(source);

        MemberPO memberPO = new MemberPO(null, "harry", encode, "哈瑞", "jack@qq.com", 1, 1, "哈瑞", "123123", 2);

        memberPOMapper.insert(memberPO);
    }

    @Test
    public void test() throws SQLException {
        Connection connection = dataSource.getConnection();
//        logger.debug(connection.toString());
    }
}