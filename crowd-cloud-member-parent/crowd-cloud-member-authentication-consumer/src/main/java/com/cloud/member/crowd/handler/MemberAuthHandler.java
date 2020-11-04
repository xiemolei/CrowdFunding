package com.cloud.member.crowd.handler;

import com.cloud.member.crowd.api.MySQLRemoteService;
import com.cloud.member.crowd.api.RedisRemoteService;
import com.cloud.member.crowd.config.ShortMessageProperties;
import com.cloud.member.crowd.entity.po.MemberPO;
import com.cloud.member.crowd.entity.vo.MemberLoginVO;
import com.cloud.member.crowd.entity.vo.MemberVo;
import com.mo.crowd.constant.CrowdConstant;
import com.mo.crowd.util.CrowdUtil;
import com.mo.crowd.util.ResultEntity;
import org.bouncycastle.util.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * @author : xiemogaminari
 * create at:  2020-10-24  23:29
 * @description:
 */
@Controller
public class MemberAuthHandler {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private ShortMessageProperties shortMessageProperties;

    // 登出
    @RequestMapping("member/auth/loginout")
    public String loginout(HttpSession session){
        session.invalidate();

        return "redirect:http://www.xmlcrowd.com:8080/";
    }

    // 登陆
    @RequestMapping("member/auth/do/login")
    public String login(
            @RequestParam("loginacct") String loginacct,
            @RequestParam("userpswd") String userpswd,
            ModelMap modelMap,
            HttpSession session
    ){
        // 查询用户
        ResultEntity<MemberPO> resultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);
        // 查询错误
        if(ResultEntity.FAILED.equals(resultEntity.getResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
            return "member-login";
        };
        // 用户不存在
        MemberPO memberPO = resultEntity.getData();
        if(memberPO == null){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }

        // 比较密码
        String userpswdDataBase = memberPO.getUserpswd();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches(userpswd, userpswdDataBase);
        if(!matches){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);

            return "member-login";
        }

        // 3.创建MemberLoginVO对象存入Session域
        MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(), memberPO.getUsername(), memberPO.getEmail());
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER, memberLoginVO);
        // 登陆成功 重定向到返回页面
        return "redirect:http://www.xmlcrowd.com:8080/auth/member/to/center/page";
    }

    // 注册
    @RequestMapping("member/autn/register")
    public String register(MemberVo memberVo, ModelMap modelMap){
        // 获取redis中的缓存验证码
        String phoneNum = memberVo.getPhoneNum();
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
        ResultEntity<String> redisEntity = redisRemoteService.getRedisValueByKeyRemote(key);
        // 获取失败
        if(ResultEntity.FAILED.equals(redisEntity.getResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, redisEntity.getMessage());
            return "member-reg";
        }

        String code = redisEntity.getData();
        // 缓存value为空/失效
        if(code == null){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
            return "member-reg";
        }

        String formCode = memberVo.getCode();
        // 验证码验证
        if(!Objects.areEqual(code, formCode)){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);
            return "member-reg";
        }
        // 删除redis缓存
        redisRemoteService.removeRedisValueByKeyRemote(key);

        // 密码加密
        String formUserpswd = memberVo.getUserpswd();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordAfterEncode = passwordEncoder.encode(formUserpswd);
        memberVo.setUserpswd(passwordAfterEncode);

        MemberPO memberPO = new MemberPO();
        BeanUtils.copyProperties(memberVo,memberPO);


        // 调用远程方法 执行注册
        ResultEntity<String> savaResultEntity = mySQLRemoteService.saveMember(memberPO);
        if(ResultEntity.FAILED.equals(savaResultEntity.getResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, savaResultEntity.getMessage());

            return "member-reg";
        }

        // 使用重定向避免刷新浏览器导致重新执行注册流程
        return "redirect:http://www.xmlcrowd.com:8080/auth/member/to/login/page";
    }


    // 发短信
    @ResponseBody
    @RequestMapping("member/auth/send/shortmessge")
    public ResultEntity<String> sendShortMessge(
            @RequestParam("phonenum") String  phonenum
    ){
        ResultEntity<String> resultEntity = CrowdUtil.sendShortMessge(
                shortMessageProperties.getHost(),
                shortMessageProperties.getPath(),
                shortMessageProperties.getMethod(),
                shortMessageProperties.getAppCode(),
                phonenum,
                shortMessageProperties.getTemplateId()
        );

        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            String code = resultEntity.getData();

            String key = CrowdConstant.REDIS_CODE_PREFIX + phonenum;

            ResultEntity<String> redisResult = redisRemoteService.setsetRedisKeyValueRemoteWithTimeout(key, code, 10, TimeUnit.MINUTES);

            if(ResultEntity.SUCCESS.equals(redisResult.getResult())){
                return ResultEntity.successWithoutData();
            }else {
                return redisResult;
            }
        } else {
            return resultEntity;
        }
    }
}