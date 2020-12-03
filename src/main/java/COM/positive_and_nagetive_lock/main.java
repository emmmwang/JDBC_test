package COM.positive_and_nagetive_lock;

/**
 * 悲观锁和乐观锁的概念
 *      悲观锁: 事物必须排队执行，数据锁住了，---不允许并发----（行级锁:select后面添加for update）
 *      乐观所: 支持并发，事物也不需要排队，只不过需要一个版本号
 *      锁是在并发的时候用！！！
 * ename    job        sal     version
 * |bank| manager|  2845131|    1.1
 *
 * 事务1--> 读取到版本号1.1
 * 事务2--> 读取到版本号1.1
 *
 * 其中事务1先修改了，修改之后看了版本号是1.1，
 * 于是提交修改的数据，将版本号修改为1.2
 *
 * 其中事务2后修改的，修改之后准备提交的时候，
 * 发现版本号是1.2，和它最初读的版本号不一致，回滚。
 *
 */

import COM.tool_class_package.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 这个程序开启一个事务，这个事务专门进行查询，并且使用行级锁/悲观锁，锁住相关数据。
 */
public class main {
    public static void main(String[] args) {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            conn= DBUtil.getConnection();
            //开启事务
            conn.setAutoCommit(false);
            String sql ="select ename ,job,sal from emp where job=? for update";
            ps=conn.prepareStatement(sql);
            ps.setString(1,"MANAGER");
            rs=ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("ename")+","+rs.getString("job")+","+rs.getDouble("sal"));
            }
            //提交事务(事务结束)
            conn.commit();
        } catch (SQLException e) {
            if(conn!=null){
                try {
                    //回滚(事务结束)
                    conn.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }finally {
            DBUtil.close(conn,ps,rs);
        }
    }
}
