package com.mo.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mo.crowd.entity.Role;
import com.mo.crowd.entity.RoleExample;
import com.mo.crowd.mapper.RoleMapper;
import com.mo.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : xiemogaminari
 * create at:  2020-10-09  16:35
 * @description:
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getRolePageInfo(Integer pageNum, Integer pageSize, String keyword) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // mapper获取分页信息
        List<Role> roleList = roleMapper.getRoleByKeyword(keyword);


        return new PageInfo<>(roleList);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void editRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void removeRole(List<Integer> list) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(list);
        roleMapper.deleteByExample(roleExample);
//        for (int id : list
//             ) {
//            roleMapper.deleteByPrimaryKey(id);
//        }
    }

    @Override
    public List<Role> getAssignRole(Integer adminId) {
        return roleMapper.getAssignRole(adminId);
    }

    @Override
    public List<Role> getUnAssignRole(Integer adminId) {
        return roleMapper.getUnAssignRole(adminId);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.selectUnAssignedRole(adminId);
    }
}