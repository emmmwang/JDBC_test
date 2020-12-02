package COM.bank_account_transfer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * sql脚本：
 *  drop table if exists t_act;
 *  creat table t_act(
 *      actno bigint,
 *      balance double(7,2); //7代表有效数字，2代表小数位
 *  );
 *  INSERT INTO t_act(catno,balance) values(111,20000);
 *  insert into t_act(actno,balance) values(222,0);
 *  commit;
 *  select*from t_act;
 *
 *  重点三行代码
 *      conn.setAutoCommit(false);
 *      conn.commit();
 *      conn.rollback();
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
            //将自动提交机制改为手动提交机制
            conn.setAutoCommit(false);//开启事务
            //3.获取预编译的数据库操作对象
            String sql ="update t_act set balance=? where actno=?";
            ps=conn.prepareStatement(sql);

            //给？传值
            ps.setDouble(1,1000);
            ps.setInt(2,111);
            int count=ps.executeUpdate();

            //人工制造异常！！！(空指针异常--没有赋初值）
            //String s=null;
            //s.toString();

            ps.setDouble(1,1000);
            ps.setInt(2,222);
            count+=ps.executeUpdate();

            System.out.println(count==2?"转账成功":"转账失败");
            //程序能走到这里说明以上程序没有异常，事物结束，手动提交数据
            conn.commit();//提交事物
        } catch (Exception e) {
            if(conn!=null){
                try{
                    conn.rollback();//回滚事务
                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
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
