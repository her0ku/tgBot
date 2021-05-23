package com.exampletelegram.tgbot.Service;

import com.exampletelegram.tgbot.Entity.Order;

import java.sql.*;

public class StatusService {
    private static final String url = "jdbc:mysql://localhost:3306/my_db";
    private static final String user = "bestuser";
    private static final String pass = "bestuser";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public String checkStatus(String userName){
        String query = "select status_order from my_db.order where tg_name = '" + userName + "'";
        try{
            con = DriverManager.getConnection(url, user, pass);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                Order order = new Order();
                order.setStatusOrder(rs.getString(1));
                    String result = order.getStatusOrder();
                    return result;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
        return null;
    }
}
