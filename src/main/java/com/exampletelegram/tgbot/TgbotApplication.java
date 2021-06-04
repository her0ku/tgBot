package com.exampletelegram.tgbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class TgbotApplication {

    public static void main(String[] args) throws TelegramApiException { //это просто зупуск бота
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class); //получаем телеграАПИ
        try{
            telegramBotsApi.registerBot(new Bot()); //регистрируем бота
        } catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
        SpringApplication.run(TgbotApplication.class, args); //Запускаем бота
    }
}
