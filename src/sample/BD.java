package sample;

import javafx.application.Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BD {


    public static Connection connection;

    public static void c(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost/salim?useUnicode=true&characterEncoding=UTF-8&useSSL=false","root","0000");
            System.out.println("connect");
            connection= con;
        } catch (Exception e){
            System.out.println(e.getMessage());
            connection=  null;
        }
    }

    public static Connection connect()  {
        return connection;
    }
}
