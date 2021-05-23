package com.exampletelegram.tgbot.Service;

import com.exampletelegram.tgbot.Entity.Order;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private static final String url = "jdbc:mysql://localhost:3306/my_db";
    private static final String user = "bestuser";
    private static final String pass = "bestuser";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    private List<Order> orderList = new ArrayList<>();

    public List<Order> showOrders(String userName){
        String query = "select * from my_db.order where tg_name = '" + userName + "'";
        try{
            con = DriverManager.getConnection(url, user, pass);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                Order order = new Order();
                order.setItemName(rs.getString(3));
                order.setPrice(rs.getInt(5));
                order.setStatusOrder(rs.getString(6));
                orderList.add(order);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { orderList.clear(); }
        }
        return orderList;
    }
}
