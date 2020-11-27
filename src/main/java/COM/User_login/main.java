package COM.User_login;

import javafx.scene.SceneAntialiasing;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
实现功能：
    1.需求模拟用户登录功能的实现
    2.业务描述
        程序运行时，提供一个输入入口，可以让用户输入用户名和密码
        用户输入用户名和密码后，提交信息，java程序收集到用户信息
        java程序连接数据库验证用户名和密码是否合法
        合法：显示登陆成功
        不合法：显示登陆失败
    3.数据准备：
        在实际开发中，表的设计会使用专业的建模工具，我们这里安装一个建模工具: PowerDesigner
        使用PD工具来进行数据库表的设计。（参见uer_login.sql脚本）
    4.当前程序存在问题
        用户名:
        fasa
        密码:
        fada' or '1'='1
        true
        这种现象被称为SQL注入(安全隐患)
        用户输入的信息中含有sql语句的关键字，并且这些关键字参与了SQL语句的编译过程，
        导致sql语句的原意被扭曲，进而达到sql注入 。
 */
public class main {
    public static void main(String[] args) {
        //1.初始化一个界面
        Map<String,String> userLoginInfo=initUI(); ///------------为啥要用map? 因为传的数据是不同的数据类型！！
        //验证用户名和密码
        boolean loginSuccess= login(userLoginInfo);
        System.out.println(loginSuccess);
    }

    /**
     * 用户登陆
     * @param userLoginInfo 用户登陆信息
     * @return false表示失败  ，true表示成功
     */
    private static boolean login(Map<String, String> userLoginInfo) {
        //打标记的意识
        boolean loginSuccess=false;
        //单独定义变量
        String loginName =userLoginInfo.get("loginName");
        String loginPwd =userLoginInfo.get("loginPwd");
        //JDBC代码
        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        try {
            //1 注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2 获取连接
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/empdb","root","123456");
            //3 获取数据库连接对象
            stmt=conn.createStatement();
            //4 执行sql语句
            String sql ="select * from t_user where loginName ='"+loginName+"' and loginPwd ='"+loginPwd+"'";
            //以上代码完成了sql语句的拼接，下面代码是发送sql语句给DBMS，DBMS进行sql编译。
            //正好将用户提供的”非法信息“编译进去。导致原sql的含义被扭曲了。
            //5 处理结果集
            rs=stmt.executeQuery(sql);
            if(rs.next()){
                //登陆成功
                loginSuccess=true;
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6 释放资源
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(stmt!=null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return loginSuccess;
    }

    /**
     * 初始haul用户界面
     * @return 用户输入的用户名和密码等登陆信息
     */

    private static Map<String, String> initUI() {
        Scanner s=new Scanner(System.in);
        System.out.println("用户名:");
        String loginName=s.nextLine();
        System.out.println("密码:");
        String loginPwd =s.nextLine();
        Map<String,String> userLoginInfo=new HashMap<>();
        userLoginInfo.put("loginName",loginName);
        userLoginInfo.put("loginPwd",loginPwd);
        return userLoginInfo;
    }

}
