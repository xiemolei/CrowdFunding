package com.cloud.member.crowd.handler;

import com.cloud.member.crowd.entity.vo.DetailProjectVO;
import com.cloud.member.crowd.entity.vo.PortalTypeVO;
import com.cloud.member.crowd.entity.vo.ProjectVO;
import com.cloud.member.crowd.service.api.ProjectProviderService;
import com.mo.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : xiemogaminari
 * create at:  2020-11-04  00:00
 * @description:
 */
@RestController
public class ProjectProviderHandler {

    @Autowired
    private ProjectProviderService projectProviderService;

    @RequestMapping("/get/portaltype/projectdata/remote")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote(){
        try {
            List<PortalTypeVO> list = projectProviderService.getPortalTypeVo();

            return ResultEntity.successWithData(list);
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        }
    };

    @RequestMapping("/save/projectVo/remote")
    public ResultEntity<String> saveProjectVoRemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId){
        try {
            projectProviderService.saveProjectPo(projectVO, memberId);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/project/detail/remote")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@RequestParam("projectId") Integer projectId){
        try {
            DetailProjectVO detailProjectVO = projectProviderService.getDetailProjectVO(projectId);

            return ResultEntity.successWithData(detailProjectVO);

        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        }
    }
}