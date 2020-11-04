package com.cloud.member.crowd.api;

import com.cloud.member.crowd.entity.po.MemberPO;
import com.cloud.member.crowd.entity.vo.DetailProjectVO;
import com.cloud.member.crowd.entity.vo.PortalTypeVO;
import com.cloud.member.crowd.entity.vo.ProjectVO;
import com.mo.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author : xiemogaminari
 * create at:  2020-10-21  22:09
 * @description:
 */
@FeignClient("crowd-mysql")
public interface MySQLRemoteService {
    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);

    @RequestMapping("/save/member/remote")
    public ResultEntity<String> saveMember(@RequestBody MemberPO memberPO);

    @RequestMapping("/get/portaltype/projectdata/remote")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote();

    @RequestMapping("/save/projectVo/remote")
    public ResultEntity<String> saveProjectVoRemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId);

    @RequestMapping("/get/project/detail/remote")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@RequestParam("projectId") Integer projectId);
}