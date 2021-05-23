package com.exampletelegram.tgbot.Service;

import java.sql.*;

public class UserService {
    private static final String url = "jdbc:mysql://localhost:3306/my_db";
    private static final String user = "bestuser";
    private static final String pass = "bestuser";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    boolean hasUser = false;

    public boolean findUser(String userName){
        String query = "SELECT tg_name FROM my_db.user where tg_name = '"+ userName + "'";
        try{
            con = DriverManager.getConnection(url, user, pass);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                hasUser = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) {
            }
        }
        return hasUser;
    }
}
