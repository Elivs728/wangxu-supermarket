package com.wangxu.DAO.role;

import com.wangxu.DAO.BaseDao;
import com.wangxu.POJO.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao{
    //获取角色列表
    public List<Role> getRoleList(Connection connection) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Role> roleList = new ArrayList<Role>();
        if (connection!=null){
            String sql = "select * from smbms_role";
            Object[] params = {};
            try {
                resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql, params);
                while (resultSet.next()){
                    Role _role = new Role();
                    _role.setId(resultSet.getInt("id"));
                    _role.setRoleName(resultSet.getString("roleName"));
                    _role.setRoleCode(resultSet.getString("roleCode"));
                    roleList.add(_role);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                BaseDao.closeResource(null,preparedStatement,resultSet);
            }
        }
        return roleList;
    }
}
