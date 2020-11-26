package COM.JDBC;

import java.net.PasswordAuthentication;
import java.sql.*;

public class main {
    public static void main(String[] args) {
        Statement stmt=null;
        Connection conn=null;
        //1.注册驱动
        Driver driver= null;  //多态，父类型引用指向子类对象
        try {
            driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            //2.获取连接
        /*
        url :统一资源定位符（网络中某个资源的绝对路径）
        https://www.baidu.com/这就是URL

            协议
            ip
            PORT（端口）
            资源名
        http://18.61.200.7/index.html
            http://  通信协议
            182.61.200.7  服务器IP地址
            80 服务器上软件的端口
            index.html是服务器上某个资源名


        jdbc:mysql://127.0.0.1:3306/emp
            jdbc:mysql://  协议
            12.0.0.1  IP地址
            3306 mysql数据库 端口号
            emp  具体的数据库实例名
        说明 ： localhost和127.0.0.1都是本机IP地址


        什么是通信协议，有什么用？
            通信协议是通信之前就提前订好的数据传输格式。
            数据包具体怎么传数据，格式提前订好的。
         */

            String url="jdbc:mysql://127.0.0.1:3306/empdb";
            String  user="root";
            String  password ="123456";
            conn =DriverManager.getConnection(url,user, password);
            System.out.println("数据库连接对象"+conn);
            //3.获取数据库操作对象(专门执行SQL语句）
            stmt= conn.createStatement();
            //4.执行sql语句
            String sql="insert into t_user(username,password,name )values('王小王',456,'wangxiaowang')";
            //专门执行DML语句的（insert delete updata）
            //返回值是“影响数据库中的记录条数
            int count =stmt.executeUpdate(sql);
            System.out.println(count ==1?"保存成功":"保存失败");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //6.释放资源
            //为了保证资源一定释放，在finally语句块中关闭资源
            //并遵循从小到大依次关闭
            //分别对其try..catch
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

        //5.处理查询结果集




}
}
