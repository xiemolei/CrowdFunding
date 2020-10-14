package com.mo.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.mo.crowd.entity.Admin;

import java.util.List;

/**
 * @author : xiemogaminari
 * create at:  2020-10-04  20:37
 * @description:
 */
public interface AdminService {
    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getAdminByKeyword(String keyword, int pageNum, int pageSize);

    void removeAdmin(Integer id);

    Admin getAdminById(Integer adminId);

    void update(Admin admin);

    void removeOldARRelationship(Integer adminId);


    void saveNewARRelationship(Integer adminId, List<Integer> roleIdList);

    Admin getAdminByLoginAcct(String username);
}


