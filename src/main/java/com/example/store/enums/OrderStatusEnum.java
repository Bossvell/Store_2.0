package com.example.store.enums;

public enum OrderStatusEnum {

    НОВЫЙ("Новый заказ"),
    ОБРАБАТЫВАЕТСЯ("Заказ находится в обработке"),
    ОПЛАЧЕН ("Заказ оплачен, передается в доставку"),
    ДОСТАВКА ("Заказ передан в службу доставки"),
    ВЫПОЛНЕН ("Заказ выполнен");

    private final String displayName;

    OrderStatusEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getValue(){
        return displayName;
    }
}
