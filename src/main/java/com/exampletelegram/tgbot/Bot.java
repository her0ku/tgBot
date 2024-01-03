package com.exampletelegram.tgbot;

        import com.exampletelegram.tgbot.Entity.Order;
        import com.exampletelegram.tgbot.Service.OrderService;
        import com.exampletelegram.tgbot.Service.StatusService;
        import com.exampletelegram.tgbot.Service.UserService;
        import org.springframework.stereotype.Component;
        import org.telegram.telegrambots.bots.TelegramLongPollingBot;
        import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
        import org.telegram.telegrambots.meta.api.objects.Update;
        import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

        import java.util.ArrayList;
        import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    private static String TOKEN = "185G4U"; //ТОКЕН
    private static String NAME = "mewShop_bot"; //НАЗВАНИЕ БОТА
    private static String status = ""; //ПЕРЕМЕННАЯ СТАТУС
    // static означает, что он принадлежит классу и, следовательно, является общим для всех экземпляров
    private static String nickName = ""; //переменная никнейма
    private static String chat_id = ""; //переменная чат id
    private CheckStatus myStatus = new CheckStatus(); //создаю объект класса checkStatus() для проверки статуса заказа
    //пока он просто объявлен


    public Bot(){}

    private static String subStatus = ""; //доп переменная для проверки статуса
    private OrderService orderService = new OrderService(); //объект для заказов
    private UserService userService = new UserService(); //объект для проверки пользователя
    private List<Order> orderList = new ArrayList<>(); //пустой список где потом будут находится заказы
    private StatusService statusService = new StatusService(); //обхъект статус заказа

    @Override
    public String getBotUsername() {
        return NAME;
    } //переопределяем Имя бота для инициализации в момент коипиляции

    @Override
    public String getBotToken() {
        return TOKEN;
    } //переопределяем ТОКЕН бота для инициализации в момент коипиляции

    @Override
    public void onUpdateReceived(Update update) { //Переопределяем метод onUpdateReceived, куда помещаем объект UPDATE
        //UPDATE своего рода сообщение которое посылаешь боту

        chat_id = String.valueOf(update.getMessage().getChatId()); //Получаем id чата
      //  String subString = statusService.checkStatus(String.valueOf(update.getMessage().getChat().getUserName()));
        if(update.hasMessage()){ //если в апдейте есть сообщение то
            if(update.getMessage().hasText()){ //если в update полученом сообзение есть текст то
               // String nickName = "@" + String.valueOf(update.getMessage().getChat().getUserName());
                nickName = "@test";
                if(update.getMessage().getText().equals("/start")) { //если полученный текст эквевалентен (равен) /start
                    try {
                        execute(new SendMessage(chat_id,"Подробную информацию о том как работает бот, вы пожете получить написав - /help")); //выодим сообщение
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                if(update.getMessage().getText().equals("/status")) //если полученный текст эквевалентен (равен) /status
                {
                        myStatus.enableStatus(); //вызываем метод кторый делает переменну isActive - true
                        new Thread(myStatus).start(); //запускается поток на выполнение
                }
                if(update.getMessage().getText().equals("/stop")) //если полученный текст эквевалентен (равен) /stop
                {
                    myStatus.disableStatus(); //вызываем метод кторый делает переменну isActive - false, тем самым прекращается выполнение потока
                    try {
                        execute(new SendMessage(chat_id,"Проверка статуса остановлена")); //выводим сообщение
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                }
                if(update.getMessage().getText().equals("/order")){ //Если полученный текст эквевалентен (равен) /order
                    try {
                        boolean hasUser = userService.findUser(nickName); //То с начала проверяем существует ли вообще пользователь
                        if(hasUser == true){ //Если существует то
                            orderList = orderService.showOrders(nickName); //Выводим список его заказов т.е вызываем у объекта orderService метод showOrders и передаем параметр никейм
                            //и он возвращает в качестве резульата список с заказами

                            execute(new SendMessage(chat_id, "Ваша Корзина товаров:\n")); //просто сообщение
                            for(Order o: orderList){ //Далее в цикле forEach берем объект из списка и выводим его
                                execute(new SendMessage(chat_id, o.toString())); //поскольку execute ожидает увидеть объект o как строку, приходится её переводить в topString()
                            }
                        }else{
                            execute(new SendMessage(chat_id, "Мы не смогли найти ваш аккаунт в магазине, видимо вы не зарегестрировались!")); //Если пользователя с таки никоми не нашли в БД, то выводится это сообщение
                        }
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                if(update.getMessage().getText().equals("/help")){ //Если полученный текст эквевалентен (равен) /help
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
    public class CheckStatus extends Thread{ //Это метод проверки статуса заказа в режиме онлайн
        private boolean isActive; // булевая переменная для прекращение работы потока
        void enableStatus() //метод который присваивает переменной true и тем самым начинается работа потока
        {
            isActive = true;
        }

        void disableStatus() //методы который присваивает переменной false и тем самым завешается работа потока
        {
            isActive = false;
        }

        @Override
        public void run() { //переопределяем метод работы потока
            while(isActive) //пока статус true
            {
                try {
                    Thread.sleep(3000); //ждём три секунды, чтобы опрос к базе был меньше
                    //Если например приложением будут пользоваться 1000 человек, то значение sleep нужно будет увеличить
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                status = statusService.checkStatus(nickName); //получаем первоначальный статус заказа
                if (!status.equals(subStatus)) { //если статус не равен вспомогательной перменной
                    subStatus = status; //то запишем в спомогательную переменную текущи статус
                    //это нужно, чтобы выводить сообщения только при изменении статуса заказа
                    try {
                        execute(new SendMessage(chat_id, "Ваш заказ " + subStatus)); //выводи сообщения
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
