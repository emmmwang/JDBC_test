package COM.registerDriverMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class main {
    public static void main(String[] args) {

        try{
            //1.注册驱动
            //这是注册驱动的第一种写法
            //DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            //注册驱动的第二种方式： 常用的。
            //为什么这种方式常用？因为参数是一个字符串，字符串可以写到xxx.properties文件中
            //一下方法不需要写接受返回值，因为我们只想用他的类加载动作
            Class.forName("com.mysql.cj.jdbc.Driver()");

            //2.获取连接
            Connection conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/empdb","root","123456");
            //com.mysql.cj.jdbc.ConnectionImpl@5ba3f27a
            System.out.println(conn);
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}

