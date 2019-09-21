
package com.progressivecoder.ecommerce.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateFailureInvoiceCommand {

	@TargetAggregateIdentifier
    public final String paymentId;

    public final String orderId;

    public CreateFailureInvoiceCommand(String paymentId, String orderId) {
        this.paymentId = paymentId;
        this.orderId = orderId;
    }
}
