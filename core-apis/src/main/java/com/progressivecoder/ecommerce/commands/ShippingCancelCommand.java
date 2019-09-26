package com.progressivecoder.ecommerce.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ShippingCancelCommand {

    @TargetAggregateIdentifier
    public final String shippingId;

    public final String orderId;

    public final String paymentId;

    public ShippingCancelCommand(String shippingId, String orderId, String paymentId) {
        this.shippingId = shippingId;
        this.orderId = orderId;
        this.paymentId = paymentId;
    }
}
