package com.wangxu.DAO.user;

import com.wangxu.POJO.Role;
import com.wangxu.POJO.User;

import java.sql.Connection;
import java.util.List;

public interface UserDao {
    //得到要登陆的用户和获取数据库连接
    public User getLoginUser(Connection connection,String userCode );
    //修改当前用户密码
    public int updatePwd(Connection connection,int id ,String password);
    //根据用户名或者角色名查询用户总数
    public int getUserCount(Connection connection,String username ,int userRole);
    //获取用户列表
    public List<User> getUserList(Connection connection,String userName,int UserRole , int CurrentPageNo,int PageSize);
    /**
     * 增加用户信息
     * @param connection
     * @param user
     * @return
     * @throws Exception
     */
    public int add(Connection connection, User user)throws Exception;

    /**
     * 通过userId删除user
     * @param delId
     * @return
     * @throws Exception
     */
    public int deleteUserById(Connection connection, Integer delId)throws Exception;

    /**
     * 通过userId获取user
     * @param connection
     * @param id
     * @return
     * @throws Exception
     */
    public User getUserById(Connection connection, String id)throws Exception;
    /**
     * 修改用户信息
     * @param connection
     * @param user
     * @return
     * @throws Exception
     */
    public int modify(Connection connection, User user)throws Exception;
}
