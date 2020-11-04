package com.cloud.member.crowd.handler;

import com.cloud.member.crowd.entity.po.MemberPO;
import com.cloud.member.crowd.service.api.MemberProviderService;
import com.mo.crowd.constant.CrowdConstant;
import com.mo.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : xiemogaminari
 * create at:  2020-10-21  15:48
 * @description:
 */
@RestController
public class MemberProviderHandler {
    @Resource
    private MemberProviderService memberProviderService;

    Logger logger = LoggerFactory.getLogger(MemberProviderHandler.class);

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct){

        try {
            MemberPO memberPOByLoginAcctRemote = memberProviderService.getMemberPOByLoginAcctRemote(loginacct);

            return ResultEntity.successWithData(memberPOByLoginAcctRemote);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    };

    @RequestMapping("/save/member/remote")
    public ResultEntity<String> saveMember(@RequestBody MemberPO memberPO){
        logger.info(memberPO.toString());

        try {
            memberProviderService.saveMemberPo(memberPO);
        } catch (Exception e) {
            if(e instanceof DuplicateKeyException){
                return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
            return  ResultEntity.failed(e.getMessage());
        }
        return ResultEntity.successWithoutData();
    };
}