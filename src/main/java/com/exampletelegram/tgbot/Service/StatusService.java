package com.exampletelegram.tgbot.Service;

import com.exampletelegram.tgbot.Entity.Order;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service //Значит 1. Это автоматическое обноружение при сканировании пакетов, Это бизнес логика прилоэения
public class StatusService {
    private static final String url = "jdbc:mysql://localhost:3306/my_db";
    private static final String user = "bestuser";
    private static final String pass = "bestuser";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public String checkStatus(String userName){
        String query = "select status_order from my_db.order where tg_name = '" + userName + "'";
        //Этот запрос значит выбрать статус заказа из таблицы заказ где имя тг совпадает с именем из параметра checkStatus(String userName)
        try{
            con = DriverManager.getConnection(url, user, pass);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                Order order = new Order(); //Создаем обхект заказ
                order.setStatusOrder(rs.getString(1)); //Помещаем результат выборки из БД а именно статус заказа
                    String result = order.getStatusOrder(); // записываем результат заказа в стринг
                    return result; //возвращаем результат
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally { //по умолчанию закрываем всё
            try { con.close(); } catch(SQLException se) {  }
            try { stmt.close(); } catch(SQLException se) {  }
            try { rs.close(); } catch(SQLException se) {  }
        }
        return null; //возвращаем null т.е ничего изменений нет
    }
}
