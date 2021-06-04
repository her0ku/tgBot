package com.exampletelegram.tgbot.Service;

import org.springframework.stereotype.Service;

import java.sql.*;


@Service //Значит 1. Это автоматическое обноружение при сканировании пакетов, Это бизнес логика прилоэения
public class UserService {
    private static final String url = "jdbc:mysql://localhost:3306/my_db";
    private static final String user = "bestuser";
    private static final String pass = "bestuser";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    boolean hasUser = false; //булевая переменная для проверки на существование юзера в БД

    public boolean findUser(String userName){
        String query = "SELECT tg_name FROM my_db.user where tg_name = '"+ userName + "'";
        //выбрать по телеграм_имени из базы пользователя где телеграм имя совпадает с параметром из findUser(String userName)
        try{
            con = DriverManager.getConnection(url, user, pass);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) { //если существует такой резуольтат
                hasUser = true; //существует значит true
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try { con.close(); } catch(SQLException se) {}
            try { stmt.close(); } catch(SQLException se) {}
            try { rs.close(); } catch(SQLException se) {
            }
        }
        return hasUser; //возвращаем результат поиска true или false
    }
}
