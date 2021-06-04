package com.exampletelegram.tgbot.Entity;

public class Order { //Просто класс "Заказ"

    private Integer id; //Переменная целочисленного типа id
    private String itemName; //Строка имя товара
    private Integer price; //цена
    private String statusOrder; //статус заказа

    public Order() {
    }

    //get -> значит получить Id    getId - значит получить id
    //set -> значит присвоить setId (значит присвоить id)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }


    //Это нужно чтобы не приводить и не писать при выводе объекта .toString(), переобпределяем здесь, чтобы добиться читаемости обеъкта
    @Override
    public String toString() {
        return  "\nНазвание товара= " + itemName +
                "\nЦена= " + price +
                "\nСтатус заказа= " + statusOrder;
    }
}
