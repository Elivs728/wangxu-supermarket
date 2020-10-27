package com.wangxu.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

//操作数据公共类
public class BaseDao {
    //先定义数据库操作对象
    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    //静态代码块
    static {
        //为了提升操作权限所以把properties的定义从try-catch中拿出来
        Properties properties = new Properties();
        //通过类加载器读取对应资源，获取类加载器-->把他获取到的资源作为流
        InputStream inputStream = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            //从输入字节流读取属性列表（键和元素对）。
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //使用此属性列表中指定的键搜索属性。
        driver=properties.getProperty("driver");
        url=properties.getProperty("url");
        username=properties.getProperty("username");
        password=properties.getProperty("password");
    }
    //获取数据库连接
    public static Connection getConnection(){
        //为了提升操作权限所以把connection的定义从try-catch中拿出来
        Connection connection = null;
        try {
            //返回与driver关联地址。
            Class.forName(driver);
            //尝试建立与数据库URL的连接。
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    //编写查询公共类
    //预编译的sql在后面执行就可以了
    public static ResultSet execute(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet,String sql,Object[] params) throws SQLException {
        //创建一个 PreparedStatement对象，用于将参数化的SQL语句发送到数据库。
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i <params.length; i++) {
            //setObject,占位符从1开始，但是我们的数组是从0开始，所以下面的索引是i+1
            //使用给定对象设置指定参数的值。
            preparedStatement.setObject(i+1,params[i]);
        }
        //执行此 PreparedStatement对象中的SQL查询，并返回查询 PreparedStatement的 ResultSet对象。
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
    //编写增删改公共方法
    public static int execute(Connection connection, PreparedStatement preparedStatement,String sql, Object[] params) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i <params.length; i++) {
            //setObject,占位符从1开始，但是我们的数组是从0开始，所以下面的索引是i+1
            preparedStatement.setObject(i+1,params[i]);
        }
        //执行在该SQL语句PreparedStatement对象，它必须是一个SQL数据操纵语言（DML）语句，如INSERT ， UPDATE或DELETE ; 或不返回任何内容的SQL语句，例如DDL语句。
        int updateRows = preparedStatement.executeUpdate();
        return updateRows;
    }
    //释放资源
    public static boolean closeResource(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
        boolean flag = true;
        if (resultSet!=null){
            try {
                resultSet.close();
                //GC回收
                resultSet=null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag=false;
            }
        }
        if (preparedStatement!=null){
            try {
                preparedStatement.close();
                //GC回收
                preparedStatement=null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag=false;
            }
        }
        if (connection!=null){
            try {
                connection.close();
                //GC回收
                connection=null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag=false;
            }
        }
        return flag;
    }
}
