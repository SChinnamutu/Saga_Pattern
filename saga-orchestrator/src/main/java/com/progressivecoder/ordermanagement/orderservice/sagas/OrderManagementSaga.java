package com.progressivecoder.ordermanagement.orderservice.sagas;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import com.progressivecoder.ecommerce.commands.CreateFailureInvoiceCommand;
import com.progressivecoder.ecommerce.commands.CreateFailureOrderCommand;
import com.progressivecoder.ecommerce.commands.CreateFailureShippingCommand;
import com.progressivecoder.ecommerce.commands.CreateInvoiceCommand;
import com.progressivecoder.ecommerce.commands.CreateShippingCommand;
import com.progressivecoder.ecommerce.commands.UpdateOrderStatusCommand;
import com.progressivecoder.ecommerce.events.InvoiceCreatedEvent;
import com.progressivecoder.ecommerce.events.OrderCreatedEvent;
import com.progressivecoder.ecommerce.events.OrderShippedEvent;
import com.progressivecoder.ecommerce.events.OrderUpdatedEvent;
import com.progressivecoder.ordermanagement.orderservice.constants.OrderStatus;

@Saga
public class OrderManagementSaga {

	private Logger logger = Logger.getLogger(OrderManagementSaga.class.getName());
	
    @Inject
    private transient CommandGateway commandGateway;

	@SuppressWarnings("rawtypes")
	@StartSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(OrderCreatedEvent orderCreatedEvent) {
		String paymentId = UUID.randomUUID().toString();
		logger.info("Saga invoked");
		// associate Saga
		SagaLifecycle.associateWith("paymentId", paymentId);
		logger.info("order id" + orderCreatedEvent.orderId);
		// send the commands
		commandGateway.send(new CreateInvoiceCommand(paymentId, orderCreatedEvent.orderId),
				new CommandCallback<CreateInvoiceCommand, Void>() {
					@Override
					public void onSuccess(CommandMessage commandMessage, Void result) {
						logger.info("Confirm transaction is dispatched successfully to InvoiceCreatedEvent !");
						logger.info("CreateInvoiceCommand processed successfully!");
					}

					@Override
					public void onFailure(CommandMessage commandMessage, Throwable cause) {
						logger.severe("********* WOW!!!" + cause);
						logger.log(Level.INFO, "CreateFailureOrderCommand called");
						commandGateway.send(new CreateFailureOrderCommand(orderCreatedEvent.orderId));
					}
				});
	}

	@SuppressWarnings("rawtypes")
	@SagaEventHandler(associationProperty = "paymentId")
	public void handle(InvoiceCreatedEvent invoiceCreatedEvent) {
		String shippingId = UUID.randomUUID().toString();
		logger.info("Saga continued");
		// associate Saga with shipping
		SagaLifecycle.associateWith("shipping", shippingId);
		// send the create shipping command
		commandGateway.send(
				new CreateShippingCommand(shippingId, invoiceCreatedEvent.orderId, invoiceCreatedEvent.paymentId),
				new CommandCallback<CreateShippingCommand, Void>() {
					@Override
					public void onSuccess(CommandMessage commandMessage, Void result) {
						logger.info("Confirm transaction is dispatched successfully!");
						logger.info("CreateShippingCommand processed successfully!");
					}

					@Override
					public void onFailure(CommandMessage commandMessage, Throwable cause) {
						logger.severe("********* WOW!!!" + cause);
						//CreateFailureInvoiceCommand is group of removing the entry in Order , Payment .
						logger.log(Level.INFO, "CreateFailureInvoiceCommand called");
						commandGateway.send(new CreateFailureInvoiceCommand(invoiceCreatedEvent.paymentId,
								invoiceCreatedEvent.orderId));
					}
				});
	}

	@SagaEventHandler(associationProperty = "orderId")
	public void handle(OrderShippedEvent orderShippedEvent) {
		commandGateway
				.send(new UpdateOrderStatusCommand(orderShippedEvent.orderId, String.valueOf(OrderStatus.SHIPPED)),
						new CommandCallback<UpdateOrderStatusCommand, Void>() {
					@Override
					public void onSuccess(CommandMessage commandMessage, Void result) {
						logger.info("Confirm transaction is dispatched successfully!");
						logger.info("CreateShippingCommand processed successfully!");
					}

					@Override
					public void onFailure(CommandMessage commandMessage, Throwable cause) {
						// I can do with three commands or writing call all events
						logger.severe("********* WOW!!!" + cause);
						//CreateFailureInvoiceCommand is group of removing the entry in Order , Payment and  Shipping.
						logger.log(Level.INFO, "CreateFailureInvoiceCommand called");
						commandGateway.send(new CreateFailureShippingCommand(
								orderShippedEvent.shippingId, orderShippedEvent.orderId,orderShippedEvent.paymentId));
					}
				});
	}

	@SagaEventHandler(associationProperty = "orderId")
	public void handle(OrderUpdatedEvent orderUpdatedEvent) {
		SagaLifecycle.end();
	}
}