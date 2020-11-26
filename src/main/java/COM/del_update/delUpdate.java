package COM.del_update;

import java.sql.*;

public class delUpdate {
    public static void main(String[] args) {
        Connection conn=null;
        Statement stmt=null;
        try{
            //1.注册驱动
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            //2.获取连接
            conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/empdb","root","123456");
            //3.获取数据库连接对象
            stmt=conn.createStatement();
            //4.执行sql语句
            //JDBC中sql语句不需要提供分号结尾
            //String sql ="delete from t_user where id=3";//删除
            String sql ="update t_user set id=3 where id=4";//更新
            int count=stmt.executeUpdate(sql);
            //System.out.println(count==1?"删除成功":"删除失败");
            System.out.println(count==1?"更新成功":"更新失败");
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            //释放资源
            if(stmt!=null){
                try {
                    stmt.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
