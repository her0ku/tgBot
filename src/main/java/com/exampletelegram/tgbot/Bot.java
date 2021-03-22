package com.exampletelegram.tgbot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
@Component
public class Bot extends TelegramLongPollingBot {

    private static String TOKEN = "1669342294:AAEbx6C17mCjhf6g0HuXo3NV-sXJJvHsY2o";
    private static String NAME = "New_String_Bot";

    @Override
    public String getBotUsername() {
        return NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String chat_id = String.valueOf(update.getMessage().getChatId());
        try
        {
            execute(new SendMessage(chat_id, "Hi "));
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }
}
