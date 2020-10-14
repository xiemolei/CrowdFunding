package com.mo.crowd.mvc.handler;

import com.mo.crowd.entity.Auth;
import com.mo.crowd.entity.Role;
import com.mo.crowd.service.api.AdminService;
import com.mo.crowd.service.api.AuthService;
import com.mo.crowd.service.api.RoleService;
import com.mo.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author : xiemogaminari
 * create at:  2020-10-10  22:00
 * @description:
 */
@Controller
public class AssignRoleHandler {
    @Autowired
    AdminService adminService;

    @Autowired
    RoleService  roleService;

    @Autowired
    private AuthService authService;

    @ResponseBody
    @RequestMapping("/assign/do/role/assign/auth.json")
    public ResultEntity<String> saveRoleAuthRelathinship(
            @RequestBody Map<String, List<Integer>> map) {

        authService.saveRoleAuthRelathinship(map);

        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(
            @RequestParam("roleId") Integer roleId) {

        List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);

        System.out.println(authIdList.toString());

        return ResultEntity.successWithData(authIdList);
    }

    @ResponseBody
    @RequestMapping("/assgin/get/all/auth.json")
    public ResultEntity<List<Auth>> getAllAuth() {

        List<Auth> authList = authService.getAll();

        return ResultEntity.successWithData(authList);
    }
    @RequestMapping("assign/to/role/assign/page.html")
    public String toAssignRolePage(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            @RequestParam("adminId") Integer adminId,
            ModelMap modelMap
    ){
        List<Role> assignRoleList = roleService.getAssignRole(adminId);

        List<Role> unAssignRoleList = roleService.getUnAssignRole(adminId);

        modelMap.addAttribute("assignRoleList", assignRoleList);
        modelMap.addAttribute("unAssignRoleList", unAssignRoleList);
        return "assign-role";
    }

    @RequestMapping("/assign/do/role/assign.html")
    public String saveAdminRoleRelationship(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("keyword") String keyword,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList
    ){
        adminService.removeOldARRelationship(adminId);

        if (roleIdList != null && roleIdList.size() > 0) {
            adminService.saveNewARRelationship(adminId, roleIdList);
        }

        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }
}