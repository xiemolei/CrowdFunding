package com.cloud.member.crowd.service.api;

import com.cloud.member.crowd.entity.po.ProjectPO;
import com.cloud.member.crowd.entity.vo.DetailProjectVO;
import com.cloud.member.crowd.entity.vo.PortalTypeVO;
import com.cloud.member.crowd.entity.vo.ProjectVO;
import com.mo.crowd.util.ResultEntity;

import java.util.List;

/**
 * @author : xiemogaminari
 * create at:  2020-11-04  00:02
 * @description:
 */
public interface ProjectProviderService {
    List<PortalTypeVO> getPortalTypeVo();

    void saveProjectPo(ProjectVO projectVO, Integer memberId);

    DetailProjectVO getDetailProjectVO(Integer projectId);
}

