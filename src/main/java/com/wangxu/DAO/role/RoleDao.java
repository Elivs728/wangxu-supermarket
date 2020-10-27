package com.wangxu.DAO.role;

import com.wangxu.POJO.Role;

import java.sql.Connection;
import java.util.List;

public interface RoleDao {
    //获取角色列表
    public List<Role> getRoleList(Connection connection);
}
