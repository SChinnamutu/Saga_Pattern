package com.progressivecoder.ecommerce.events;

public class InvoiceCancelEvent  {

    public final String paymentId;

    public final String orderId;

    public InvoiceCancelEvent(String paymentId, String orderId) {
        this.paymentId = paymentId;
        this.orderId = orderId;
    }
}
