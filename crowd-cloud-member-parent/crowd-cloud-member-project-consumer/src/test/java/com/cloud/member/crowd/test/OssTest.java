package com.cloud.member.crowd.test;

import com.cloud.member.crowd.config.OSSProperties;
import com.mo.crowd.util.CrowdUtil;
import com.mo.crowd.util.ResultEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author : xiemogaminari
 * create at:  2020-10-30  23:38
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OssTest {

    Logger logger = LoggerFactory.getLogger(OssTest.class);

    @Autowired
    private OSSProperties ossProperties;


    /**
     * 测试OSS存放文件服务
     * @throws FileNotFoundException
     */
    @Test
    public void ossTest() throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("333.JPG");

        ResultEntity<String> resultEntity = CrowdUtil.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                inputStream,
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                "333.JPG");

        logger.info(resultEntity.toString());
    }

}