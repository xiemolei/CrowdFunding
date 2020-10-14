package com.mo.crowd.mvc.handler;

import com.mo.crowd.entity.Admin;
import com.mo.crowd.entity.Student;
import com.mo.crowd.service.api.AdminService;
import com.mo.crowd.util.CrowdUtil;
import com.mo.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : xiemogaminari
 * create at:  2020-10-04  22:09
 * @description:
 */
@Controller
public class TestMapping {
    @Autowired
    private AdminService adminService;

    Logger logger = LoggerFactory.getLogger(TestMapping.class);


    @RequestMapping("/test/ssm.html")
    public String testSsm(ModelMap modelMap , HttpServletRequest request){
        boolean judgeResult = CrowdUtil.judgeRequestType(request);

        logger.info("judgeResult="+judgeResult);

        List<Admin> adminList = adminService.getAll();

        modelMap.addAttribute("adminList", adminList);


        System.out.println(10/0);

        return "target";
    }

    @ResponseBody
    @RequestMapping("send/array/three.html")
    public String testReceiveThree(@RequestBody List<Integer> arr){

        for (int a:arr
             ) {
            logger.info("lever-info--parma:"+a);
        }

        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/compose/object.json")
    public ResultEntity<Student> testReceiveComposeObject(@RequestBody Student student, HttpServletRequest request) {

        boolean judgeResult = CrowdUtil.judgeRequestType(request);

        logger.info("judgeResult="+judgeResult);

        logger.info(student.toString());

        // 将“查询”到的Student对象封装到ResultEntity中返回
        ResultEntity<Student> resultEntity = ResultEntity.successWithData(student);

        int a = 10/0;

        return resultEntity;
    }
}