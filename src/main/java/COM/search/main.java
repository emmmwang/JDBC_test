package COM.search;

import com.sun.source.tree.TryTree;

import java.security.cert.TrustAnchor;
import java.sql.*;

public class main {
    public static void main(String[] args) {
        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        try {
            //1、注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2、获取连接
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/empdb","root","123456");
            //3、获取数据库连接对象
            stmt=conn.createStatement();
            //4、执行sql语句
            String sql="select id as idAddr,name,username from t_user";
            //rs=stmt.executeUpdate(sql);//execute()方法只能执行增删改(DML语句)  返回改动数量
            rs=stmt.executeQuery(sql);//executeQuery方法专门执行查询语句  返回结果集
            //5、处理查询结果集
            //next()是一个改变指针方法，使指针指向下一行,如果下一行有数据，返回true，否则返回false
            while (rs.next()){
                //光标指向的行有数据
                //取数据
                // 1，2,3指的是第几列
                //getString()方法的特点是：不管数据库类型是什么，都已String类型取出
                //这里需要查询的就是上面sql语句中需要查询的，顺序要一致！！！

                //可以以String 类型取出表中的所有数据，也可以根据表中--对应--数据类型，用该数据类型来取出该数据
                /*
                String id= rs.getString("1");//JDBC中所有下标从1开始。不是从0开始
                String name=rs.getString("2");// 1，2,3指的是第几列
                String username =rs.getString("3");
                System.out.println(id+","+name+","+username);
                */

                //为啥要这样写？  增强程序的健壮性
                int id= rs.getInt("idAddr");//根据sql中定义的数据来写获取啥---select id as idAddr...
                String name=rs.getString("name");
                String username =rs.getString("username");//重点注意： 列名称不是表中的列名称，是查询结果集的列名称
                System.out.println(id+","+name+","+username);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //6、释放资源
            if(rs!=null){
                try {
                    rs.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(stmt!=null){
                try {
                    stmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


    }
}
