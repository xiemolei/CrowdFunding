package com.mo.crowd.test;

import com.mo.crowd.entity.Role;
import com.mo.crowd.mapper.AdminMapper;
import com.mo.crowd.entity.Admin;
import com.mo.crowd.mapper.RoleMapper;
import com.mo.crowd.service.api.AdminService;
import com.mo.crowd.service.api.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author : xiemogaminari
 * create at:  2020-09-29  03:03
 * @description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml" , "classpath:spring-persist-tx.xml"})
public class CrowdTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;

    @Test
    public void inserTest(){
        for(int i=0;i<268;i++){
            Admin admin = new Admin(null, "Acct"+i, "123456"+i, "username"+i, "email@qq.com"+1, null);
            adminService.saveAdmin(admin);
        }
    }

    @Test
    public void inserRole(){
        for(int i=0;i<20;i++){
            Role role = new Role(null, i+"name");
            roleService.saveRole(role);
        }
    }

    @Test
    public void testTx() {
        Admin admin = new Admin(null, "jerry", "123456", "杰瑞", "jerry@qq.com", null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void testLogger(){
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        logger.info("Info level");
        logger.info("Info level");
        logger.info("Info level");
        logger.info("Info level");

        logger.debug("debug level");
        logger.debug("debug level");
        logger.debug("debug level");
        logger.debug("debug level");

        logger.warn("warn level");
        logger.warn("warn level");
        logger.warn("warn level");
        logger.warn("warn level");

        logger.error("error level");
        logger.error("error level");
        logger.error("error level");
        logger.error("error level");
    }
    @Test
    public void testInsertAdmin(){
        Admin admin = new Admin(null,"张三","123456","zhangsan","12344@qq.com",null);
        int insert = adminMapper.insert(admin);
        System.out.println(insert);
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

    }
}