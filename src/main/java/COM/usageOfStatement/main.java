package COM.usageOfStatement;

import java.sql.*;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        /*
        //用户在控制台输入desc就是降序，输入asc就是升序
        Scanner s=new Scanner(System.in);
        System.out.println("请输入desc或asc，desc表示降序，asc表示升序");
        System.out.println("请输入:");
        String KeyWords=s.nextLine();

        //执行sql
        Connection conn =null ;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取连接
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/empdb","root","123456");
            //获取预编译的数据库操作对象
            String sql ="select loginName from t_user order by loginName ?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,KeyWords);
            //执行sql
            ps.executeQuery();
            //遍历结果集
            while(rs.next()){
                System.out.println(rs.getString("loginName"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
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
        */

        //用户在控制台输入desc就是降序，输入asc就是升序
        Scanner s=new Scanner(System.in);
        System.out.println("请输入desc或asc，desc表示降序，asc表示升序");
        System.out.println("请输入:");
        String KeyWords=s.nextLine();

        //执行sql
        Connection conn =null ;
        Statement stmt=null;
        ResultSet rs=null;
        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取连接
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/empdb","root","123456");
            //获取数据库操作对象
            stmt=conn.createStatement();
            //执行sql
            String sql="select loginName from t_user order by loginName " + KeyWords;
            rs=stmt.executeQuery(sql);
            //遍历结果集
            while(rs.next()){
                System.out.println(rs.getString("loginName"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
    }
}
