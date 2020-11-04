package com.mo.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.mo.crowd.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : xiemogaminari
 * create at:  2020-10-09  16:34
 * @description:
 */
public interface RoleService {
    PageInfo<Role> getRolePageInfo(Integer pageNum, Integer pageSize, String keyword);

    void saveRole(Role role);

    void editRole(Role role);

    void removeRole(List<Integer> list);

    List<Role> getAssignRole(Integer adminId);

    List<Role> getUnAssignRole(Integer adminId);
}