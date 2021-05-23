package com.exampletelegram.tgbot;

        import com.exampletelegram.tgbot.Entity.Order;
        import com.exampletelegram.tgbot.Service.OrderService;
        import com.exampletelegram.tgbot.Service.StatusService;
        import com.exampletelegram.tgbot.Service.UserService;
        import org.springframework.stereotype.Component;
        import org.telegram.telegrambots.bots.TelegramLongPollingBot;
        import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
        import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
        import org.telegram.telegrambots.meta.api.objects.Message;
        import org.telegram.telegrambots.meta.api.objects.Update;
        import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
        import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
        import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

        import java.util.ArrayList;
        import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    private static String TOKEN = "1851881145:AAFwx0V6u0ayo2b-IzuSdpSkLudhghPNG4U";
    private static String NAME = "mewShop_bot";
    public Bot(){}

    private String subStatus = "";
    private OrderService orderService = new OrderService();
    private UserService userService = new UserService();
    private List<Order> orderList = new ArrayList<>();
    private StatusService statusService = new StatusService();

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
      //  String subString = statusService.checkStatus(String.valueOf(update.getMessage().getChat().getUserName()));
        if(update.hasMessage()){
            if(update.getMessage().hasText()){
               // String nickName = "@" + String.valueOf(update.getMessage().getChat().getUserName());
                String nickName = "@test";
                if(update.getMessage().getText().equals("/start")) {
                    try {
                        execute(new SendMessage(chat_id,"Подробную информацию о том как работает бот, вы пожете получить написав - /help"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                if(update.getMessage().getText().equals("/status"))
                {
                    try {
                        while (update.getMessage().getText().equals("/status")) {
                            String status = statusService.checkStatus(nickName);
                            Thread.sleep(5000);
                            if (!status.equals(subStatus)) {
                                subStatus = status;
                                execute(new SendMessage(chat_id, "Ваш заказ " + subStatus));
                            } else {
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(update.getMessage().getText().equals("/order")){
                    try {
                        boolean hasUser = userService.findUser(nickName);
                        if(hasUser == true){
                            orderList = orderService.showOrders(nickName);
                            execute(new SendMessage(chat_id, "Ваша Корзина товаров:\n"));
                            for(Order o: orderList){
                                execute(new SendMessage(chat_id, o.toString()));
                            }
                        }else{
                            execute(new SendMessage(chat_id, "Мы не смогли найти ваш аккаунт в магазине, видимо вы не зарегестрировались!"));
                        }
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                if(update.getMessage().getText().equals("/help")){
                    try {
                        execute(new SendMessage(chat_id, "Справочная:\n" +
                                "/start - позволяет запустить бота, видимо вы это уже сделали, поздравляем!\n" +
                                "/help - вызов навигации по боту, вы это тоже уже сделали, ещераз поздравляем!\n" +
                                "/order - позволяет узнать, что вы добавили в корзину, если вдруг забыли)\n" +
                                "/status - нажимая на status, вы включаете мониторинг вашего заказа, это позволит молнеиносно узнать на каком этапе формирования ваш заказ"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
