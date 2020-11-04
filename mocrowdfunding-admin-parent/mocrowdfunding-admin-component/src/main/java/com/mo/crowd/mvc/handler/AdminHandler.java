package com.mo.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.mo.crowd.constant.CrowdConstant;
import com.mo.crowd.entity.Admin;
import com.mo.crowd.exception.LoginFailedException;
import com.mo.crowd.service.api.AdminService;
import com.mo.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.util.List;

/**
 * @author : xiemogaminari
 * create at:  2020-10-06  15:36
 * @description:
 */
@Controller
public class AdminHandler {
    Logger logger = LoggerFactory.getLogger(AdminHandler.class);

    @Autowired
    AdminService adminService;

    // update
    @RequestMapping("admin/update.html")
    public String update(
            Admin admin,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword
    ){
        adminService.update(admin);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    // 修改页
    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            ModelMap modelMap
    ){
        Admin admin = adminService.getAdminById(adminId);
        modelMap.addAttribute("admin", admin);

        return "admin-edit";
    }

    // 新增
    @RequestMapping("/admin/save.html")
    public String saveAdmin(Admin admin){
        adminService.saveAdmin(admin);

        return "redirect:/admin/get/page.html?pageNum="+Integer.MAX_VALUE;
    }

    // 删除
    @RequestMapping("/admin/remove/{id}/{pageNum}/{keyword}.html")
    public String remove(
            @PathVariable("id") Integer id,
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("keyword") String keyword
    ){
        adminService.removeAdmin(id);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    // admin分页
    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(
            // RequestParam的defaultValue，可以在参数为空时 自动为参数指定默认值
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            ModelMap modelMap
    ){
        // 使用getAdminByKeyword方法获得pageinfo队形
        PageInfo<Admin> pageInfo = adminService.getAdminByKeyword(keyword, pageNum, pageSize);

        // 将pageinfo对象存入模型
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);

        return "admin-page";
    }

    // 退出登陆
    @RequestMapping("/admin/do/logout.html")
    private String doLogout(HttpSession session){

        // Session失效
        session.invalidate();

        // 重定向到登陆页面
        return "redirect:/admin/to/login/page.html";
    }

    // 登陆
    @RequestMapping("/admin/do/login.html")
    public String doLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            HttpSession session
    ){
        if(loginAcct == "" || userPswd == ""){
            throw new LoginFailedException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        // 调用Service方法执行登录检查
        // 这个方法如果能够返回admin对象说明登录成功，如果账号、密码不正确则会抛出异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);

        // 将登录成功返回的admin对象存入Session域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);

        return "redirect:/admin/to/main/page.html";
    }
/*
    Spring Security中定义了四个支持使用表达式的注解，分别是@PreAuthorize、@PostAuthorize、@PreFilter和@PostFilter。
    其中前两者可以用来在方法调用前或者调用后进行权限检查，后两者可以用来对集合类型的参数或者返回值进行过滤。
    要使它们的定义能够对我们的方法的调用产生影响我们需要设置global-method-security元素的pre-post-annotations=”enabled”，默认为disabled。
*/
    @ResponseBody
    @PostAuthorize("returnObject.data.loginAcct == principal.username")
    @RequestMapping("/admin/test/post/filter.json")
    public ResultEntity<Admin> getAdminById() {

        Admin admin = new Admin();

        admin.setLoginAcct("adminOperator");

        return ResultEntity.successWithData(admin);
    }

    @PreFilter(value="filterObject%2==0")
    @ResponseBody
    @RequestMapping("/admin/test/pre/filter")
    public ResultEntity<List<Integer>> saveList(@RequestBody List<Integer> valueList) {
        return ResultEntity.successWithData(valueList);
    }
}