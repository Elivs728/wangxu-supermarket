package com.wangxu.DAO.user;

import com.mysql.jdbc.StringUtils;
import com.wangxu.DAO.BaseDao;
import com.wangxu.POJO.Role;
import com.wangxu.POJO.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    //得到需要登录的用户
    public User getLoginUser(Connection connection, String userCode) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        //通过sql语句查出登录的名字
        if (connection != null) {
            String sql = "select * from smbms_user where userCode=?";
            Object[] params = {userCode};
            try {
                resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql, params);
                if (resultSet.next()) {
                    //把查出来的信息封装到User里面
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUserCode(resultSet.getString("userCode"));
                    user.setUserName(resultSet.getString("userName"));
                    user.setUserPassword(resultSet.getString("userPassword"));
                    user.setGender(resultSet.getInt("gender"));
                    user.setBirthday(resultSet.getDate("birthday"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setAddress(resultSet.getString("address"));
                    user.setUserRole(resultSet.getInt("userRole"));
                    user.setCreatedBy(resultSet.getInt("createdBy"));
                    user.setCreationDate(resultSet.getTimestamp("creationDate"));
                    user.setModifyBy(resultSet.getInt("modifyBy"));
                    user.setModifyDate(resultSet.getTimestamp("modifyBy"));
                }
                //关闭资源
                BaseDao.closeResource(null, preparedStatement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;

    }

    //修改的当前用户密码
    public int updatePwd(Connection connection, int id, String password) {
        System.out.println("UserServlet" + password);
        //用preparedStatement执行预编译
        PreparedStatement preparedStatement = null;
        int execute = 0;
        if (connection != null) {
            String sql = "update smbms_user set userPassword = ? where id = ?";
            Object params[] = {password, id};
            try {
                execute = BaseDao.execute(connection, preparedStatement, sql, params);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return execute;
    }

    //根据用户名或者角色名查询用户总数
    public int getUserCount(Connection connection, String userName, int userRole) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole = r.id");
            ArrayList<Object> list = new ArrayList<Object>();//用来存放参数
            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%"+userName+"%");//模糊查询占位符
            } else if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            //把list转化为数组
            Object[] params = list.toArray();
            System.out.println("UserDaoImpl->getUserCount:" + sql.toString());//输出完整的sql语句
            try {
                resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql.toString(), params);
                if (resultSet.next()) {
                    count = resultSet.getInt("count");//从结果集中获取最终数量
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                BaseDao.closeResource(null, preparedStatement, resultSet);
            }
        }
        return count;
    }

    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<User>();
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<Object>();//用来存放参数
            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");//模糊查询占位符
            }
            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            //在数据库中，分页使用 limit startIndex,pageSize ;总数
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo=(currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);
            Object [] params = list.toArray();
            System.out.println("sql----->"+sql.toString());
            try {
                resultSet = BaseDao.execute(connection,preparedStatement,resultSet,sql.toString(),params);
                while (resultSet.next()){
                    //把查出来的信息封装到User里面
                    User _user = new User();
                    _user.setId(resultSet.getInt("id"));
                    _user.setUserCode(resultSet.getString("userCode"));
                    _user.setUserName(resultSet.getString("userName"));
                    _user.setGender(resultSet.getInt("gender"));
                    _user.setBirthday(resultSet.getDate("birthday"));
                    _user.setPhone(resultSet.getString("phone"));
                    _user.setUserRole(resultSet.getInt("userRole"));
                    _user.setUserRoleName(resultSet.getString("userRoleName"));
                    userList.add(_user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                BaseDao.closeResource(null,preparedStatement,resultSet);
            }
        }
        return userList;
    }

    public int add(Connection connection, User user) throws Exception {
        // TODO Auto-generated method stub
        PreparedStatement preparedStatement = null;
        int updateRows = 0;
        if(null != connection){
            String sql = "insert into smbms_user (userCode,userName,userPassword," +
                    "userRole,gender,birthday,phone,address,creationDate,createdBy) " +
                    "values(?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {user.getUserCode(),user.getUserName(),user.getUserPassword(),
                    user.getUserRole(),user.getGender(),user.getBirthday(),
                    user.getPhone(),user.getAddress(),user.getCreationDate(),user.getCreatedBy()};
            updateRows = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return updateRows;
    }

    public int deleteUserById(Connection connection, Integer delId) throws Exception {
        PreparedStatement preparedStatement = null;
        int flag = 0;
        if(null != connection){
            String sql = "delete from smbms_user where id=?";
            Object[] params = {delId};
            flag = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return flag;
    }

    public User getUserById(Connection connection, String id) throws Exception {
        // TODO Auto-generated method stub
        User user = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        if(null != connection){
            String sql = "select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.id=? and u.userRole = r.id";
            Object[] params = {id};
            rs = BaseDao.execute(connection, preparedStatement, rs, sql, params);
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
                user.setUserRoleName(rs.getString("userRoleName"));
            }
            BaseDao.closeResource(null, preparedStatement, rs);
        }
        return user;
    }

    public int modify(Connection connection, User user) throws Exception {
        // TODO Auto-generated method stub
        int flag = 0;
        PreparedStatement preparedStatement = null;
        if(null != connection){
            String sql = "update smbms_user set userName=?,"+
                    "gender=?,birthday=?,phone=?,address=?,userRole=?,modifyBy=?,modifyDate=? where id = ? ";
            Object[] params = {user.getUserName(),user.getGender(),user.getBirthday(),
                    user.getPhone(),user.getAddress(),user.getUserRole(),user.getModifyBy(),
                    user.getModifyDate(),user.getId()};
            flag = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return flag;
    }
}
