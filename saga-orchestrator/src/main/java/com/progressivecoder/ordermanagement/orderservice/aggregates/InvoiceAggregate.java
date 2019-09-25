package com.progressivecoder.ordermanagement.orderservice.aggregates;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.progressivecoder.ecommerce.commands.CreateFailureInvoiceCommand;
import com.progressivecoder.ecommerce.commands.CreateInvoiceCommand;
import com.progressivecoder.ecommerce.dto.OrderCreateDTO;
import com.progressivecoder.ecommerce.events.InvoiceCreatedErrorEvent;
import com.progressivecoder.ecommerce.events.InvoiceCreatedEvent;
import com.progressivecoder.ordermanagement.orderservice.constants.InvoiceStatus;

@Aggregate
public class InvoiceAggregate {

	private Logger log  = Logger.getLogger(OrderAggregate.class.getName());
	
    @AggregateIdentifier
    private String paymentId;

    private String orderId;

    private InvoiceStatus invoiceStatus;

    public InvoiceAggregate() {
    }

    @CommandHandler
    public InvoiceAggregate(CreateInvoiceCommand createInvoiceCommand) throws RestClientException, Exception{
		log.info("CreateInvoiceCommand called successfully");
		AggregateLifecycle.apply(new InvoiceCreatedEvent(createInvoiceCommand.paymentId, createInvoiceCommand.orderId));
		// rest call request to create
		OrderCreateDTO orderCreateDTO = new OrderCreateDTO();
		orderCreateDTO.setPaymentId(paymentId);
		orderCreateDTO.setOrderId(orderId);
		orderCreateDTO.setRequestedBy("ADMIN");
		log.info("CreateInvoiceCommand Request :: " + orderCreateDTO);
		ResponseEntity<?> responseEntity = this.invokeAPI("http://localhost:8082/api/payments", HttpMethod.POST,
				orderCreateDTO, String.class);
		String mdmMesponse =  (String) responseEntity.getBody();
		log.info("CreateInvoiceCommand Response :: " + mdmMesponse);
		log.info("CreateInvoiceCommand ends successfully");
    }

    @EventSourcingHandler
    protected void on(InvoiceCreatedEvent invoiceCreatedEvent){
        this.paymentId = invoiceCreatedEvent.paymentId;
        this.orderId = invoiceCreatedEvent.orderId;
        this.invoiceStatus = InvoiceStatus.PAID;
    }

    
    @CommandHandler
    public InvoiceAggregate(CreateFailureInvoiceCommand createFailureInvoiceCommand) throws RestClientException, Exception{
		log.info("CreateFailureInvoiceCommand called successfully");
		AggregateLifecycle.apply(
				new InvoiceCreatedEvent(createFailureInvoiceCommand.paymentId, createFailureInvoiceCommand.orderId));
		// rest call request to invoice
		OrderCreateDTO orderCreateDTO = new OrderCreateDTO();
		orderCreateDTO.setPaymentId(paymentId);
		orderCreateDTO.setOrderId(orderId);
		orderCreateDTO.setRequestedBy("ADMIN");
		log.info("CreateFailureInvoiceCommand Request :: " + orderCreateDTO);
		ResponseEntity<?> invoiceResponseEntity = this.invokeAPI("http://localhost:8082/api/payments", HttpMethod.DELETE,
				orderCreateDTO, String.class);
		String invoiceMesponse = (String) invoiceResponseEntity.getBody();
		log.info("CreateFailureInvoiceCommand Response :: " + invoiceMesponse);
		//rest call request to remove order
		log.info("Invoke OrderFailure Service begins");
		log.info("CreateFailureOrderCommand Request :: " + orderCreateDTO);
		ResponseEntity<?> orderResponseEntity =  this.invokeAPI("http://localhost:8081/api/orders",HttpMethod.DELETE,orderCreateDTO, String.class);
		log.info("Invoke OrderFailure Service ends");
        String orderMesponse =  (String) orderResponseEntity.getBody();
		log.info("CreateFailureOrderCommand Response :: " + orderMesponse);
		log.info("CreateFailureInvoiceCommand ends successfully");
    }

    @EventSourcingHandler
    protected void on(InvoiceCreatedErrorEvent invoiceCreatedErrorEvent){
        this.paymentId = invoiceCreatedErrorEvent.paymentId;
        this.orderId = invoiceCreatedErrorEvent.orderId;
    }
    
    //Generic method to call lifafa apis
  	private ResponseEntity<?> invokeAPI(String url,HttpMethod method,Object payload,Class<?> responseType) throws RestClientException, Exception{
  		ResponseEntity<?> response  = null;
  		HttpEntity<?> requestEntity = new HttpEntity<>(payload);
  		RestTemplate restTemplate = new RestTemplate(getRequestFactory());
  		response = restTemplate.exchange(url,method,requestEntity,responseType);
        return response;
  	}
    
    private final int TIMEOUT = (int) TimeUnit.SECONDS.toMillis(1000);
    
    private ClientHttpRequestFactory getRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(TIMEOUT);
        factory.setConnectTimeout(TIMEOUT);
        factory.setConnectionRequestTimeout(TIMEOUT);
        return factory;
    }
}
