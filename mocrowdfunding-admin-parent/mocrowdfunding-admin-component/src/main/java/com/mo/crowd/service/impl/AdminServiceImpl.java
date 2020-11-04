package com.mo.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mo.crowd.constant.CrowdConstant;
import com.mo.crowd.entity.Admin;
import com.mo.crowd.entity.AdminExample;
import com.mo.crowd.exception.LoginAcctAlreadyInUseException;
import com.mo.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.mo.crowd.exception.LoginFailedException;
import com.mo.crowd.mapper.AdminMapper;
import com.mo.crowd.service.api.AdminService;
import com.mo.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.directory.AttributeInUseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.Objects;

/**
 * @author : xiemogaminari
 * create at:  2020-10-04  20:37
 * @description:
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    public AdminMapper adminMapper;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    public void saveAdmin(Admin admin){
        // 密码加密
        String adminpwd = admin.getUserPswd();
//        adminpwd = CrowdUtil.md5(adminpwd);
        adminpwd = passwordEncoder.encode(adminpwd);
        admin.setUserPswd(adminpwd);

        // 创建日期
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(date);
        admin.setCreateTime(createTime);


        try {
            int insert = adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    };

    public List<Admin> getAll(){
        return adminMapper.selectByExample(new AdminExample());
    };


    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        // 1.根据登录账号查询Admin对象
        // ①创建AdminExample对象
        AdminExample adminExample = new AdminExample();
        // ②创建Criteria
        AdminExample.Criteria criteria = adminExample.createCriteria();
        // ③在Criteria中封装sql
        criteria.andLoginAcctEqualTo(loginAcct);
        // ④调用mapper查询
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        // 2.判断admins对象是否为空
        if(admins == null || admins.size() == 0){
            throw  new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if(admins.size() > 1){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
        }
        // 3.判断admin是否为空
        Admin admin = admins.get(0);
        if(admin == null){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 4.admin存在，取出密码
        String pswAdminDB = admin.getUserPswd();
        // 5.表单密码加密
//        String pswAdminForm = CrowdUtil.md5(userPswd);
        String pswAdminForm = passwordEncoder.encode(userPswd);
        // 6.比较密码
        if(!Objects.equals(pswAdminDB, pswAdminForm)){
            // 7.密码错误
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 8.如果一致则返回Admin对象
        return admin;


    }

    @Override
    public PageInfo<Admin> getAdminByKeyword(String keyword, int pageNum, int pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询结果
        List<Admin> list = adminMapper.getAdminByKeyword(keyword);

        // 返回结果
        return new PageInfo<>(list);
    }

    @Override
    public void removeAdmin(Integer id) {
        // 删除
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        // 根据ID获取用户信息
        Admin admin = adminMapper.selectByPrimaryKey(adminId);

        return admin;
    }

    @Override
    public void update(Admin admin) {

        // Selective表示null值不更新
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }        }

    }

    @Override
    public void removeOldARRelationship(Integer adminId) {
        adminMapper.removeOldARRelationship(adminId);
    }

    @Override
    public void saveNewARRelationship(Integer adminId, List<Integer> roleIdList) {
        adminMapper.saveNewARRelationship(adminId, roleIdList);
    }

    @Override
    public Admin getAdminByLoginAcct(String username) {

        AdminExample example = new AdminExample();

        AdminExample.Criteria criteria = example.createCriteria();

        criteria.andLoginAcctEqualTo(username);

        List<Admin> list = adminMapper.selectByExample(example);

        Admin admin = list.get(0);

        return admin;
    }
}