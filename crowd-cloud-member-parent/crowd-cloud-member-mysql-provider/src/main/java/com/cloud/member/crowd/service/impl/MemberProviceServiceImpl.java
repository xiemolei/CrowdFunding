package com.cloud.member.crowd.service.impl;

import com.cloud.member.crowd.entity.po.MemberPO;
import com.cloud.member.crowd.entity.po.MemberPOExample;
import com.cloud.member.crowd.mapper.MemberPOMapper;
import com.cloud.member.crowd.service.api.MemberProviderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : xiemogaminari
 * create at:  2020-10-21  22:17
 * @description:
 */
@Service
public class MemberProviceServiceImpl implements MemberProviderService {

    @Resource
    private MemberPOMapper memberPOMapper;

    public MemberPO getMemberPOByLoginAcctRemote(String loginacct) {
        // 1.创建MemberPOExample对象
        MemberPOExample memberPOExample = new MemberPOExample();

        // 2.创建Criteria对象
        MemberPOExample.Criteria criteria = memberPOExample.createCriteria();

        // 3.封装查询条件
        criteria.andLoginacctEqualTo(loginacct);

        // 4.执行查询
        List<MemberPO> memberPOS = memberPOMapper.selectByExample(memberPOExample);

        // 5.判断结果是否为空
        if (memberPOS.size() == 0 || memberPOS == null){
            return null;
        }

        // 6.返回结果对象
        return memberPOS.get(0);
    }

    public void saveMemberPo(MemberPO memberPO) {
        int insert = memberPOMapper.insert(memberPO);
    }
}