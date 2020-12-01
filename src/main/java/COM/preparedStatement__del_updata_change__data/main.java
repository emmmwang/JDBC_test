package COM.preparedStatement__del_updata_change__data;

import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class main {
    public static void main(String[] args) {
        PreparedStatement ps=null;
        Connection conn=null;
        try {
            //1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.获取连接
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/empdb","root","123456");

            /*
            String sql="insert into t_user(loginName,loginPwd,realName)values('wangxiaowang',123,'王小王')";
            ps= conn.prepareStatement(sql);
             */
            //更新数据一定要加where，要确定位置，不然会全部都更新了！！！
            /*String sql="update t_user set loginName=?,loginPwd=?,realName=?where id=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,"limanman");
            ps.setLong(2,123L);
            ps.setString(3,"李墁墁");
            */

            String sql="delete from t_user where id=?";
            ps=conn.prepareStatement(sql);
            ps.setInt(1,1);
            //4.执行sql语句
            int count=ps.executeUpdate();
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
