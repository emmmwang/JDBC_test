package COM.mohu_query;

import COM.tool_class_package.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 这个程序有两个任务
 *      第一： 测试DBUtil是否好用
 *      第二：模糊查询怎么写
 */
public class main {
    public static void main(String[] args) {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            //获取连接
            conn=DBUtil.getConnection();
            //获取预编译的数据库操作对象

            // 错误写法
            /*
            String sql ="select loginName from t_user where loginName like '_?%'";
            ps=conn.prepareStatement(sql);
            ps.setString(1,"jiangyingying");
             */

            String sql ="select loginName from t_user where loginName like ?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,"_i%");//模糊查询固定格式---  _i%(i是查询的“子集”）
            rs=ps.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString("loginName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            DBUtil.close(conn,ps,rs);
        }
    }
}
