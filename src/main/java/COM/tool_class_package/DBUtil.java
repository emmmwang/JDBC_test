package COM.tool_class_package;

import java.sql.*;

/**类目的：封装！
 * 工具类中的构造方法都是私有的。
 * 因为工具类当中的方法都是静态的，不需要new独享，直接采用类名调用。
 */
public class DBUtil {
    private DBUtil(){}

    //静态代码块在类加载时执行，并且只执行一次
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接对象
     * @return 连接对象
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {//封装有异常由调用者处理！！！
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/empdb","root","123456");
    }

    /**
     * 关闭资源
     * @param conn 连接对象
     * @param ps    数据库操作对象
     * @param rs    结果集
     */
    public static void close(Connection conn, Statement ps, ResultSet rs){
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
}
