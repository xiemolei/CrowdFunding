package com.cloud.member.crowd.handler;

import com.cloud.member.crowd.api.MySQLRemoteService;
import com.cloud.member.crowd.entity.vo.*;
import com.cloud.member.crowd.config.OSSProperties;
import com.mo.crowd.constant.CrowdConstant;
import com.mo.crowd.util.CrowdUtil;
import com.mo.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : xiemogaminari
 * create at:  2020-10-31  00:09
 * @description:
 */
@Controller
public class ProjectHandler {
    @Autowired
    private OSSProperties ossProperties;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;


    /**
     * 上传图片到OSS
     * @param picture
     * @return 返回OSS图片路径
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/create/upload/picture/return")
    public ResultEntity<String> uploadPictureReturn(
            @RequestParam("picture") MultipartFile picture) throws IOException {
        ResultEntity<String> resultEntity = CrowdUtil.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                picture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                picture.getOriginalFilename());

        return resultEntity;
    }

    @RequestMapping("/create/project/information")
    public String saveProjectInformation(
            // 表单信息
            ProjectVO projectVO,

            // headpicture
            MultipartFile headerPicture,
            List<MultipartFile> detailPictureList,

            // 收集数据存入session
            HttpSession session,

            // 出错时返回页面
            Model model
    ) throws IOException {
        // 头图判断
        boolean headPictureIsEmpty = headerPicture.isEmpty();
        if(headPictureIsEmpty){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);
            return "project-launch";
        }
        // 头图上传
        ResultEntity<String> uploadHeaderPicResultEntity = CrowdUtil.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                headerPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                headerPicture.getOriginalFilename());
        // 上传结果
        if(ResultEntity.SUCCESS.equals(uploadHeaderPicResultEntity.getResult())){
            String headerPicPath = uploadHeaderPicResultEntity.getData();
            projectVO.setHeaderPicturePath(headerPicPath);
        }else{
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
            return "project-launch";
        }


        // 详情图判断
        if(detailPictureList == null || detailPictureList.size() == 0){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
            return "project-launch";
        }
        // 详情图上传
        List<String> detailPicPathList = new ArrayList<String>();

        for(MultipartFile detailPicture : detailPictureList){
            if(detailPicture.isEmpty()){
                model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
                return "project-launch";
            }
            ResultEntity<String> uploadDetailPictureResultEntity = CrowdUtil.uploadFileToOss(
                    ossProperties.getEndPoint(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    detailPicture.getInputStream(),
                    ossProperties.getBucketName(),
                    ossProperties.getBucketDomain(),
                    detailPicture.getOriginalFilename());

            if(ResultEntity.SUCCESS.equals(uploadDetailPictureResultEntity.getResult())){
                String detailPicPath = uploadHeaderPicResultEntity.getData();
                detailPicPathList.add(detailPicPath);
            }else{
                model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
                return "project-launch";
            }
        }
        // 上传结果
        projectVO.setDetailPicturePathList(detailPicPathList);

        // 表单信息存储到session
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);

        // 返回
        return "redirect:http://www.xmlcrowd.com:8080/project/return/info/page";
    }

    @ResponseBody
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String> uploadReturnPicture(
            @RequestParam("returnPicture") MultipartFile returnPicture
    ) throws IOException {
        // 1.执行文件上传
        ResultEntity<String> uploadReturnPicResultEntity = CrowdUtil.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                returnPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                returnPicture.getOriginalFilename());

        // 2.返回上传的结果
        return uploadReturnPicResultEntity;
    }

    @ResponseBody
    @RequestMapping("/create/save/return.json")
    public ResultEntity<String> saveRetrun(
            ReturnVO returnVO,
            HttpSession session
    ){
        try {
            // 取出session中的Project
            ProjectVO projectVo =(ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
            if(projectVo == null){
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }
            // project中的return对象判空，并初始化
            List<ReturnVO> returnVOList = projectVo.getReturnVOList();
            if(returnVOList == null || returnVOList.size() == 0){
                returnVOList = new ArrayList<ReturnVO>();
                projectVo.setReturnVOList(returnVOList);
            }
            returnVOList.add(returnVO);

            // 再存回seeion
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVo);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/create/confirm")
    public String saveConfirm(
            ModelMap modelMap,
            HttpSession session,
            MemberConfirmInfoVO memberConfirmInfoVO
    ){
        ProjectVO projectVo =(ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        if(projectVo == null){
            throw new RuntimeException(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
        }
        projectVo.setMemberConfirmInfoVO(memberConfirmInfoVO);

        MemberLoginVO memberLoginVO = (MemberLoginVO)session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = memberLoginVO.getId();

        ResultEntity<String> saveResultEntity = mySQLRemoteService.saveProjectVoRemote(projectVo, memberId);

        String result = saveResultEntity.getResult();
        if(ResultEntity.FAILED.equals(result)){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveResultEntity.getMessage());

            return "project-confirm";
        }

        // 8.如果远程保存成功则跳转到最终完成页面
        return "redirect:http://www.xmlcrowd.com:8080/project/create/success";
    }

    @RequestMapping("/get/project/detail/{projectId}")
    public String getProjectDetail(
            @PathVariable("projectId") Integer projectId,
            Model model
    ){
        ResultEntity<DetailProjectVO> resultEntity = mySQLRemoteService.getDetailProjectVORemote(projectId);

        if(ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            DetailProjectVO detailProjectVO = resultEntity.getData();

            model.addAttribute("detailProjectVO", detailProjectVO);
        }

        return "project-show-detail";
    }
}