package com.exampletelegram.tgbot.model;

import java.util.List;

public class Order {
    private int orderNumber;
    private String customerName;
    private List<String> cart;
    private int price;

    public Order() {
    }

    public Order(int orderNumber, String customerName, int price) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.price = price;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<String> getCart() {
        return cart;
    }

    public int getPrice() {
        return price;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCart(List<String> cart) {
        this.cart = cart;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", customerName='" + customerName + '\'' +
                ", cart=" + cart +
                ", price=" + price +
                '}';
    }
}
