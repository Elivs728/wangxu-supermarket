package com.wangxu.service.role;

import com.wangxu.DAO.BaseDao;
import com.wangxu.DAO.role.RoleDao;
import com.wangxu.DAO.role.RoleDaoImpl;
import com.wangxu.POJO.Role;

import java.sql.Connection;
import java.util.List;

public class RoleServiceImpl implements RoleService {
    //引入Dao
    private RoleDao roleDao;
    public RoleServiceImpl(){
        roleDao  = new RoleDaoImpl();
    }
    public List<Role> getRoleList() {
        Connection connection = null;
        List<Role> roleList = null;
        //调用BaseDao类的getConnection方法
        connection = BaseDao.getConnection();
        //通过业务层调用对应的具体的数据库操作
        roleList = roleDao.getRoleList(connection);
        BaseDao.closeResource(connection,null,null);
        return  roleList;
    }
}
