package com.cloud.member.crowd.service.api;

import com.cloud.member.crowd.entity.po.MemberPO;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : xiemogaminari
 * create at:  2020-10-21  22:16
 * @description:
 */
public interface MemberProviderService {
    MemberPO getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);


    void saveMemberPo(MemberPO memberPO);
}