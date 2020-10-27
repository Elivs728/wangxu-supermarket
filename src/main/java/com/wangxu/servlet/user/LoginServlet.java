package com.wangxu.servlet.user;
import com.wangxu.POJO.User;
import com.wangxu.service.user.UserService;
import com.wangxu.service.user.UserServiceImpl;
import com.wangxu.util.Constants;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class LoginServlet extends HttpServlet {
    //servlet:控制层调用业务层代码,接收用户参数，转发视图
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet--start....");
        //获取用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        //调用业务层，和数据库中的数据进行对比
        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);//这里已经把登陆的人查出来了
        if (user!=null){
            //查有此人，可以登录
            //将用户的信息放在Session中
            req.getSession().setAttribute(Constants.USER_SESSION,user);
            //登陆成功后跳转到内部主页
            resp.sendRedirect("jsp/frame.jsp");
        }else {
            //查无此人
            //转发回登陆页面，顺带提示用户名或密码错误
            req.setAttribute("error","用户名或者密码不正确");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
