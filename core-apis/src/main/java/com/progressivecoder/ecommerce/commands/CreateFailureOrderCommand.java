package com.progressivecoder.ecommerce.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateFailureOrderCommand {

    @TargetAggregateIdentifier
    public final String orderId;

    public CreateFailureOrderCommand(String orderId) {
        this.orderId = orderId;
    }
}
