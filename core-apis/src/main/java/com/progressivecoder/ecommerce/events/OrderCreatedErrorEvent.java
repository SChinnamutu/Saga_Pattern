package com.progressivecoder.ecommerce.events;

public class OrderCreatedErrorEvent {

    public final String orderId;

    public OrderCreatedErrorEvent(String orderId) {
        this.orderId = orderId;
    }
}
