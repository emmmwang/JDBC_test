package COM.things_auto_commit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.jar.Manifest;

/**
 * JDBC事物机制
 *      1.JDBC中的事物是自动提交的，什么是自动提交？
     *      只要执行任意一条DML语句，则自动提交一次，这是JDBC默认的事物行为。
     *      但是在实际的业务当中，通常都是N跳DML语句共同关联和才能完成的，必须
     *      保证他们这些DML与在同一个事物中同时成功或同时失败
 *      2.先验证一下JDBC的事物是否是自动提交机制！
 *          测试结果：JDBC中只要执行任意一条sql语句就提交一次
 */
public class main {
    public static void main(String[] args) {
        PreparedStatement ps=null;
        Connection conn=null;
        try {
            //1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.获取连接
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/empdb","root","123456");
            //3.获取预编译的数据库操作对象
            String sql ="update t_user set loginPwd=? where id=?";
            //第一次给占位符传值
            ps=conn.prepareStatement(sql);
            ps.setLong(1,123465);
            ps.setInt(2,2);
            int count=ps.executeUpdate();//执行第一条sql语句
            System.out.println(count);

            //重新给占位符传值
            ps.setLong(1,1234657);
            ps.setInt(2,3);
            count=ps.executeUpdate();//执行第二条sql语句
            System.out.println(count);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
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
    }
}
