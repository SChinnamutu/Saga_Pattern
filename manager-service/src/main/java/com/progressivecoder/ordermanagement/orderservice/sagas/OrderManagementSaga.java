package com.progressivecoder.ordermanagement.orderservice.sagas;

import java.math.BigDecimal;
import java.util.UUID;

import javax.inject.Inject;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import com.progressivecoder.ecommerce.commands.CreateInvoiceCommand;
import com.progressivecoder.ecommerce.commands.CreateShippingCommand;
import com.progressivecoder.ecommerce.commands.InvoiceCancelCommand;
import com.progressivecoder.ecommerce.commands.OrderCancelCommand;
import com.progressivecoder.ecommerce.commands.ShippingCancelCommand;
import com.progressivecoder.ecommerce.commands.UpdateOrderStatusCommand;
import com.progressivecoder.ecommerce.events.InvoiceCreatedEvent;
import com.progressivecoder.ecommerce.events.OrderCreatedEvent;
import com.progressivecoder.ecommerce.events.OrderShippedEvent;
import com.progressivecoder.ecommerce.events.OrderUpdatedEvent;
import com.progressivecoder.ordermanagement.orderservice.aggregates.OrderStatus;

@Saga
public class OrderManagementSaga {

    @Inject
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent){
        String paymentId = UUID.randomUUID().toString();
        System.out.println("OrderCreatedEvent Ends with exception status :: false");
        //associate Saga
        SagaLifecycle.associateWith("paymentId", paymentId);
        //send the commands
        System.out.println("<------------------------CreateInvoiceCommand Event Starts--------------------->");
        System.out.println("CreateInvoiceCommand Init");
        commandGateway.send(new CreateInvoiceCommand(paymentId, orderCreatedEvent.orderId),
        		new CommandCallback<CreateInvoiceCommand, Object>() {
					@Override
					public void onResult(CommandMessage<? extends CreateInvoiceCommand> commandMessage,
							CommandResultMessage<? extends Object> commandResultMessage) {
						System.out.println("CreateInvoiceCommand Ends with exception status :: " + commandResultMessage.isExceptional());
						if(commandResultMessage.isExceptional()) {
							System.out.println("OrderCancelCommand Starts");
							commandGateway.send(new OrderCancelCommand(orderCreatedEvent.orderId, orderCreatedEvent.itemType,
									 orderCreatedEvent.price, orderCreatedEvent.currency, String.valueOf(OrderStatus.CREATED)));
							System.out.println("OrderCancelCommand Ends");
							//SagaLifecycle.end();
						}
					}
		});
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void handle(InvoiceCreatedEvent invoiceCreatedEvent){
        String shippingId = UUID.randomUUID().toString();
        System.out.println("<------------------------CreateShippingCommand Event Starts--------------------->");
        System.out.println("CreateShippingCommand Init");
        //associate Saga with shipping
        SagaLifecycle.associateWith("shipping", shippingId);
        //send the create shipping command
        commandGateway.send(new CreateShippingCommand(shippingId, invoiceCreatedEvent.orderId, invoiceCreatedEvent.paymentId),
        		new CommandCallback<CreateShippingCommand, Object>() {
					@Override
					public void onResult(CommandMessage<? extends CreateShippingCommand> commandMessage,
							CommandResultMessage<? extends Object> commandResultMessage) {
						System.out.println("CreateShippingCommand Ends with exception status :: " + commandResultMessage.isExceptional());
						if(commandResultMessage.isExceptional()) {
							System.out.println("OrderCancelCommand Starts");
							commandGateway.send(new OrderCancelCommand(invoiceCreatedEvent.orderId, "LAPTOP",
									new BigDecimal("200"), "INR", String.valueOf(OrderStatus.REJECTED)));
							System.out.println("OrderCancelCommand Ends");
							System.out.println("InvoiceCancelCommand Inits");
							commandGateway.send(new InvoiceCancelCommand(invoiceCreatedEvent.paymentId, invoiceCreatedEvent.orderId));
							System.out.println("InvoiceCancelCommand Ends");
						}
				}
		});
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderShippedEvent orderShippedEvent){
    	System.out.println("<------------------------UpdateOrderStatusCommand Event Starts--------------------->");
    	System.out.println("UpdateOrderStatusCommand Init");
        commandGateway.send(new UpdateOrderStatusCommand(orderShippedEvent.orderId, String.valueOf(OrderStatus.SHIPPED)),
        		new CommandCallback<UpdateOrderStatusCommand, Object>() {
					@Override
					public void onResult(CommandMessage<? extends UpdateOrderStatusCommand> commandMessage,
							CommandResultMessage<? extends Object> commandResultMessage) {
						System.out.println("UpdateOrderStatusCommand Ends with exception status :: "+ commandResultMessage.isExceptional());
						if(commandResultMessage.isExceptional()) {
							System.out.println("OrderCancelCommand Starts");
							commandGateway.send(new OrderCancelCommand(orderShippedEvent.orderId, "LAPTOP",
									new BigDecimal("200"), "INR", String.valueOf(OrderStatus.REJECTED)));
							System.out.println("OrderCancelCommand Ends");
							System.out.println("InvoiceCancelCommand Inits");
							commandGateway.send(new InvoiceCancelCommand(orderShippedEvent.paymentId, orderShippedEvent.orderId));
							System.out.println("InvoiceCancelCommand Ends");
							System.out.println("ShippingCancelCommand Inits");
							 commandGateway.send(new ShippingCancelCommand(orderShippedEvent.shippingId, orderShippedEvent.orderId, orderShippedEvent.paymentId));
							System.out.println("ShippingCancelCommand Ends");
						}
					}
		});
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderUpdatedEvent orderUpdatedEvent){
        SagaLifecycle.end();
    }
}
