package COM.User_login;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 解决sql注入问题
 *      只要用户提供的信息不参与编译过程，问题就解决了
 *      要想用户提供的信息不参与编译过程，就使用预编译java.sql.PreparedStatement
 *      PreparedStatement接口继承了java.sql.Statement
 *      PreparedStatement属于预编译的数据库操作对象
 *      PreparedStatement的原理:预先对SQL语句的框架进行编译，然后再给SQL语句传“值”
 *
 * statement和prepareStatement对比
 *      -statement存在sql注入问题，PreparedStatement解决了sql注入问题
 *      -是他特么net是编译一次执行一次，preparedStatement是编译一次执行n次，preparedStatement效率较高
 *      -PreparedStatement会在编译阶段做类型的安全检查
 *
 *      所以prepareStatement使用较多，只有极少数的情况下需要使用statement
 *      只有当业务方面要求必须支持sql注入的时候采用statement
 */
public class solution_sql_insert {
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
        //打标记的意识-------为啥要打标记？------>这个函数的目的只是看是否登录成功，所以用一个标记来记录一下
        boolean loginSuccess=false;
        //单独定义变量
        String loginName =userLoginInfo.get("loginName");
        String loginPwd =userLoginInfo.get("loginPwd");
        //JDBC代码
        Connection conn=null;
        PreparedStatement ps=null;//这里使用prepareStatement
        ResultSet rs=null;
        try {
            //1 注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2 获取连接
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/empdb","root","123456");
            //4 执行sql语句
            //SQL语句的框子，其中一个？，表示一个占位符，一个？将来接受一个“值”，注意:占位符不能用单引号括起来。
            String sql ="select * from t_user where loginName =? and loginPwd =?";//SQL语句的框子
            //3 获取预编译数据库连接对象
            //程序执行到此处，会发送sql语句框子给DMS，然后DMS进行sql语句的预先编译。
            ps=conn.prepareStatement(sql);
            //给占位符？传值(第一个?下标是1，第二个？下标是2，以此类推)
            ps.setString(1,loginName);
            ps.setString(2,loginPwd);
            //以上代码完成了sql语句的拼接，下面代码是发送sql语句给DBMS，DBMS进行sql编译。
            //正好将用户提供的”非法信息“编译进去。导致原sql的含义被扭曲了。
            //5 处理结果集
            //这里executeQuery()方法不需要再传入sql语句了，因为前面prepareStatement预处理已经调用sql语句了
            rs=ps.executeQuery();
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
            if(ps!=null){
                try {
                    ps.close();
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
