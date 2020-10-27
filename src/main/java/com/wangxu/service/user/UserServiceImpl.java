package com.wangxu.service.user;

import com.sun.xml.internal.bind.v2.TODO;
import com.wangxu.DAO.BaseDao;
import com.wangxu.DAO.user.UserDao;
import com.wangxu.DAO.user.UserDaoImpl;
import com.wangxu.POJO.Role;
import com.wangxu.POJO.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    //业务层都会调用dao层，所以要引入dao层
    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    public User login(String userCode, String userPassword) {
        Connection connection = null;
        User user = null;
        //调用BaseDao类的getConnection方法
        connection = BaseDao.getConnection();
        //通过业务层调用对应的具体的数据库操作
        user = userDao.getLoginUser(connection, userCode);
        BaseDao.closeResource(connection, null, null);
        if (null!=user){
            if (!user.getUserPassword().equals(userPassword)){
                user = null;
            }
        }
        return user;
    }

    public boolean updatePwd(int id, String pwd) {
        System.out.println("UserServlet"+pwd);
        Connection connection = null;
        boolean flag = false;
        connection = BaseDao.getConnection();
        //修改密码
        if (userDao.updatePwd(connection,id,pwd)>0){
            flag = true;
        }
        BaseDao.closeResource(connection,null,null);
        return flag;
    }
//查询记录数
    public int getUserCount(String username, int userRole) {
        Connection connection = BaseDao.getConnection();
        int count = userDao.getUserCount(connection, username, userRole);
        BaseDao.closeResource(connection,null,null);
        return count;
    }
    //获取用户列表

    public List<User> getUserList(String queryUserName, int queryUserRole, int CurrentPageNo, int PageSize) {
       Connection connection = null;
       List<User> userList = null;
       connection = BaseDao.getConnection();
       userList = userDao.getUserList(connection,queryUserName,queryUserRole,CurrentPageNo,PageSize);
       BaseDao.closeResource(connection,null,null);
       return userList;
    }

    public boolean add(User user) {
        // TODO Auto-generated method stub

        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);//开启JDBC事务管理
            int updateRows = userDao.add(connection,user);
            connection.commit();
            if(updateRows > 0){
                flag = true;
                System.out.println("add success!");
            }else{
                System.out.println("add failed!");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                System.out.println("rollback==================");
                connection.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }finally{
            //在service层进行connection连接的关闭
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    public User selectUserCodeExist(String userCode) {
        // TODO Auto-generated method stub
        Connection connection = null;
        User user = null;
        try {
            connection = BaseDao.getConnection();
            user = userDao.getLoginUser(connection, userCode);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return user;
    }

    public boolean deleteUserById(Integer delId) {
        // TODO Auto-generated method stub
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            if(userDao.deleteUserById(connection,delId) > 0)
                flag = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    public User getUserById(String id) {
        // TODO Auto-generated method stub
        User user = null;
        Connection connection = null;
        try{
            connection = BaseDao.getConnection();
            user = userDao.getUserById(connection,id);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            user = null;
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return user;
    }

    public boolean modify(User user) {
        // TODO Auto-generated method stub
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            if(userDao.modify(connection,user) > 0)
                flag = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }
}
