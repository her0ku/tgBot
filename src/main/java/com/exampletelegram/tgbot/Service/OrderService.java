package com.exampletelegram.tgbot.Service;

import com.exampletelegram.tgbot.Entity.Order;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service //Значит 1. Это автоматическое обноружение при сканировании пакетов, Это бизнес логика прилоэения
public class OrderService {

    private static final String url = "jdbc:mysql://localhost:3306/my_db"; //Строка подключения к БД
    private static final String user = "bestuser"; //имя юзера
    private static final String pass = "bestuser"; //пароль от БД
    private static Connection con; //соединение
    private static Statement stmt; //запрос sql
    private static ResultSet rs; //результат

    private List<Order> orderList = new ArrayList<>(); //объявляем список заказов пока что пустой

    public List<Order> showOrders(String userName){ //Этот метод вернет список заказов (String userName) - передаем имя пользователя пример: @test
        String query = "select * from my_db.order where tg_name = '" + userName + "'";  //формируем запрос
        //Выбор все из таблицы заказов где телеграм_тег совпадает и именем юзера
        try{
            con = DriverManager.getConnection(url, user, pass); //создаем коннекшн
            stmt = con.createStatement(); //создаем наше высказывание
            rs = stmt.executeQuery(query); //помещаем запрос и ожидаем результат
            while (rs.next()) { //пока курсор на следующей строке результирующего набора из базы данных и возвращает true
                Order order = new Order(); //экземпляр класса заказ
                order.setItemName(rs.getString(3)); //тут мы сетим (по факту присваиваем названию заказа полученый результат из бд колонки 3)
                order.setPrice(rs.getInt(5)); //тут мы сетим (присваиваем цену полученную из бд колонки 5)
                order.setStatusOrder(rs.getString(6)); //тут мы сетим (присваиваем статус заказа из бд колонки 6)
                orderList.add(order); //добавляем в список сформированный объект
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally { //по умолчанию закрывается коннекшн, закрывается стейтмент и закрывается результат
            try { con.close(); } catch(SQLException se) {}
            try { stmt.close(); } catch(SQLException se) {}
            try { rs.close(); } catch(SQLException se) { orderList.clear(); }
        }
        return orderList; //возвращаем список
    }
}
