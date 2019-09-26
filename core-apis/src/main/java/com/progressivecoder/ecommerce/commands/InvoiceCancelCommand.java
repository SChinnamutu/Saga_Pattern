package com.progressivecoder.ecommerce.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class InvoiceCancelCommand{

    @TargetAggregateIdentifier
    public final String paymentId;

    public final String orderId;

    public InvoiceCancelCommand(String paymentId, String orderId) {
        this.paymentId = paymentId;
        this.orderId = orderId;
    }
}
