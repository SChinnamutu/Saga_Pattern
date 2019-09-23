package com.progressivecoder.ordermanagement.orderservice.aggregates;

import java.util.concurrent.CompletableFuture;
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

import com.progressivecoder.ecommerce.commands.CreateFailureShippingCommand;
import com.progressivecoder.ecommerce.commands.CreateShippingCommand;
import com.progressivecoder.ecommerce.dto.OrderCreateDTO;
import com.progressivecoder.ecommerce.events.OrderShippedErrorEvent;
import com.progressivecoder.ecommerce.events.OrderShippedEvent;

@Aggregate
public class ShippingAggregate {

	
	private Logger log  = Logger.getLogger(OrderAggregate.class.getName());
	
    @AggregateIdentifier
    private String shippingId;

    private String orderId;

    private String paymentId;

    public ShippingAggregate() {
    }

    @CommandHandler
    public ShippingAggregate(CreateShippingCommand createShippingCommand) throws RestClientException, Exception{
		log.info("CreateShippingCommand called successfully");
		AggregateLifecycle.apply(new OrderShippedEvent(createShippingCommand.shippingId, createShippingCommand.orderId,
				createShippingCommand.paymentId));
		// rest api call
		OrderCreateDTO orderCreateDTO = new OrderCreateDTO();
		orderCreateDTO.setOrderId(orderId);
		orderCreateDTO.setPaymentId(paymentId);
		orderCreateDTO.setShippingId(shippingId);
		orderCreateDTO.setRequestedBy("ADMIN");
		log.info("CreateShippingCommand Request :: " + orderCreateDTO);
		ResponseEntity<?> responseEntity = this.invokeAPI("http://localhost:8083/api/ship", HttpMethod.POST,
				orderCreateDTO, String.class);
		String mdmMesponse = (String) responseEntity.getBody();
		log.info("CreateShippingCommand Response :: " + mdmMesponse);
		log.info("CreateShippingCommand ends successfully");
    }

    @EventSourcingHandler
    protected void on(OrderShippedEvent orderShippedEvent){
        this.shippingId = orderShippedEvent.shippingId;
        this.orderId = orderShippedEvent.orderId;
    }
    
    
    @CommandHandler
    public ShippingAggregate(CreateFailureShippingCommand createFailureShippingCommand) throws RestClientException, Exception{
		log.info("CreateShippingCommand called successfully");
        AggregateLifecycle.apply(new OrderShippedEvent(createFailureShippingCommand.shippingId, createFailureShippingCommand.orderId, createFailureShippingCommand.paymentId));
        //rest api call	
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO();
        orderCreateDTO.setOrderId(orderId);
        orderCreateDTO.setPaymentId(paymentId);
        orderCreateDTO.setShippingId(shippingId);
        orderCreateDTO.setRequestedBy("ADMIN");
        log.info("CreateFailureShippingCommand Request :: " + orderCreateDTO);
        ResponseEntity<?> responseEntity =  this.invokeAPI("http://localhost:8083/api/ship",
				HttpMethod.DELETE,orderCreateDTO, CompletableFuture.class);
		String mdmMesponse = (String)responseEntity.getBody();
		log.info("CreateFailureShippingCommand Response :: " + mdmMesponse);
		log.info("CreateShippingCommand ends successfully");
    }

    @EventSourcingHandler
    protected void on(OrderShippedErrorEvent orderShippedErrorEvent){
        this.shippingId = orderShippedErrorEvent.shippingId;
        this.orderId = orderShippedErrorEvent.orderId;
        this.paymentId = orderShippedErrorEvent.paymentId;
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
