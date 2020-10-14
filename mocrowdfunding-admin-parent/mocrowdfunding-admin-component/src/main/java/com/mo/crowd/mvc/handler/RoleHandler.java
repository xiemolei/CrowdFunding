package com.mo.crowd.mvc.handler;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mo.crowd.entity.Role;
import com.mo.crowd.service.api.RoleService;
import com.mo.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author : xiemogaminari
 * create at:  2020-10-09  16:33
 * @description:
 */
@Controller
public class RoleHandler {
    @Autowired
    RoleService roleService;

    @ResponseBody
    @RequestMapping("role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getRoleInfo(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword
    ){
        PageInfo<Role> rolePageInfo = roleService.getRolePageInfo(pageNum, pageSize, keyword);

        return ResultEntity.successWithData(rolePageInfo);
    }

    @ResponseBody
    @RequestMapping("role/save.json")
    public  ResultEntity<String> saveRole(Role role){

        roleService.saveRole(role);

        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("role/update.json")
    public ResultEntity<String> editRole(Role role){
        roleService.editRole(role);

        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("role/remove/by/role/id/array.json")
    public ResultEntity<String> removeRole(@RequestBody List<Integer> list){
        roleService.removeRole(list);
        return ResultEntity.successWithoutData();
    }
}