package COM.getMysqlInfoToFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

//将拦截数据库的所有信息配置到配置文件中
public class main {
    public static void main(String[] args) {
        //bundle??  就是一个容器，用于保存数据，其中数据是以
        // key-value(键对值)的形式存在

        //使用资源绑定器绑定属性配置文件
        ResourceBundle bundle=ResourceBundle.getBundle("jdbc");
        String driver=bundle.getString("driver");
        String url=bundle.getString("url");
        String user=bundle.getString("user");
        String password=bundle.getString("password");

        Connection conn=null;
        Statement stmt=null;
        try{
            //1.注册驱动
            Class.forName(driver);
            //2.获取连接
            //getConnection的三个参数，连接地址，用户名，用户密码
            conn=DriverManager.getConnection(url,user,password);
            //3.获取数据库连接对象
            stmt=conn.createStatement();
            //4.执行sql语句
            String sql ="update t_user set id=4 where id=3";//更新
            int count=stmt.executeUpdate(sql);
            //System.out.println(count==1?"删除成功":"删除失败");
            System.out.println(count==1?"更新成功":"更新失败");
        }catch (Exception e){
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

