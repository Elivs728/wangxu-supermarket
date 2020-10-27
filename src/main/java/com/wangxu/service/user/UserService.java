package com.wangxu.service.user;

import com.wangxu.POJO.Role;
import com.wangxu.POJO.User;

import java.sql.Connection;
import java.util.List;

public interface UserService {
    //用户登录
    public User login(String userCode , String userPassword);
    //根据用户id修改密码
    public boolean updatePwd(int id , String pwd);
    //查询记录数
    public int getUserCount(String username , int userRole);
    //查询用户列表
    public List<User> getUserList(String userName, int UserRole , int CurrentPageNo, int PageSize);
    /**
     * 增加用户信息
     * @param user
     * @return
     */
    public boolean add(User user);
    /**
     * 根据userCode查询出User
     * @param userCode
     * @return
     */
    public User selectUserCodeExist(String userCode);
    /**
     * 根据ID删除user
     * @param delId
     * @return
     */
    public boolean deleteUserById(Integer delId);

    /**
     * 根据ID查找user
     * @param id
     * @return
     */
    public User getUserById(String id);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public boolean modify(User user);
}
