package com.exampletelegram.tgbot.Entity;

import javax.persistence.Entity;

public class Order {

    private Integer id;
    private String itemName;
    private Integer price;
    private String statusOrder;

    public Order() {
    }

    public Order(String itemName, Integer price, String statusOrder) {
        this.itemName = itemName;
        this.price = price;
        this.statusOrder = statusOrder;
    }

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

    @Override
    public String toString() {
        return  "\nНазвание товара= " + itemName +
                "\nЦена= " + price +
                "\nСтатус заказа= " + statusOrder;
    }
}
