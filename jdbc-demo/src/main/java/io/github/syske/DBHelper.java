package io.github.syske;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {

    private static String driver = "com.mysql.jdbc.Driver";   //数据库驱动
    //连接数据库的URL地址
    private static final String url = "jdbc:mysql://localhost:3307/userlogin?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    private static final String usename = "root";
    private static final String password = "root"; //数据库密码

    private static Connection connection =null;
    //静态代码块负责加载驱动
    static{//静态块中的代码会优先被执行
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //单例模式返回数据库连接对象
    public static Connection getConnection(){
        if(connection==null){
            try {
                connection = DriverManager.getConnection(url,usename,password);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
            return connection;
        }
        return connection;
    }
    public static void main(String[] args) {
        ResultSet resultSet = null;
        try{
            Connection connection = DBHelper.getConnection();
            Statement statement =  connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM user;");
            while(resultSet.next()){
                System.out.println(resultSet.getString("username"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}