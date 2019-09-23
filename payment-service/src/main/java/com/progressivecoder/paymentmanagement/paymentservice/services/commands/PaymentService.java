package com.progressivecoder.paymentmanagement.paymentservice.services.commands;

import com.progressivecoder.ecommerce.dto.OrderCreateDTO;
import com.progressivecoder.ecommerce.dto.OrderDTO;

public interface PaymentService {

    public String createOrder(OrderCreateDTO orderCreateDTO);

	public String deleteOrder(OrderDTO orderDTO);

}
