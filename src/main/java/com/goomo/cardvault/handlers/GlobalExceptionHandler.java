package com.goomo.cardvault.handlers;

import java.io.IOException;

import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ws.client.WebServiceIOException;

import com.goomo.cardvault.constants.MessageCodes;
import com.goomo.cardvault.dto.StatusMessage;
import com.goomo.cardvault.exceptions.ExceptionResponseModel;


/**
 * This class used to define global and custom exceptions for server internal error, custom, bad request, time out etc,.
 * @author Manjunath Jakkandi
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	/**
	 * This method used to throw server internal error at global level
	 * @author Manjunath Jakkandi
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponseModel> generalException(Exception e) throws Exception{
		log.error(e.getMessage());
		ExceptionResponseModel model = new ExceptionResponseModel();
		model.setStatus(MessageCodes.INTERNAL_SERVER_ERROR);
		model.setStatusMessage(new StatusMessage(MessageCodes.INTERNAL_SERVER_ERROR_MSG, "Internal Server Error. Please try again later."));
		return new ResponseEntity<ExceptionResponseModel>(model, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	/**
	 * This method used to send custom exception error messages at blobal level
	 * @author Manjunath Jakkandi
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ExceptionResponseModel> customException(CustomException e) throws Exception{
		log.error(e.getMessage());
		ExceptionResponseModel model = new ExceptionResponseModel();
		if(e.getMessage().equalsIgnoreCase(MessageCodes.BAD_REQUEST)) {
			model.setStatus(MessageCodes.BAD_REQUEST);
			model.setStatusMessage(new StatusMessage(MessageCodes.BAD_REQUEST_MSG, MessageCodes.BAD_REQUEST_DESC));
			return new ResponseEntity<ExceptionResponseModel>(model, HttpStatus.INTERNAL_SERVER_ERROR);
		}else if(e.getMessage().equalsIgnoreCase(MessageCodes.INVALID_RESPONSE)) {
			model.setStatus(MessageCodes.INVALID_RESPONSE);
			model.setStatusMessage(new StatusMessage(MessageCodes.INVALID_RESPONSE_MSG, MessageCodes.INVALID_RESPONSE_DESC));
			return new ResponseEntity<ExceptionResponseModel>(model, HttpStatus.INTERNAL_SERVER_ERROR);
		}else if(e.getMessage().equalsIgnoreCase(MessageCodes.NON_AUTHORATIVE_INFORMATION)) {
			model.setStatus(MessageCodes.NON_AUTHORATIVE_INFORMATION);
			model.setStatusMessage(new StatusMessage(MessageCodes.NON_AUTHORATIVE_INFORMATION_MSG, MessageCodes.NON_AUTHORATIVE_INFORMATION_DESC));
			return new ResponseEntity<ExceptionResponseModel>(model, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}else if(e.getMessage().equalsIgnoreCase(MessageCodes.UN_AUTHORISATION)) {
			model.setStatus(MessageCodes.UN_AUTHORISATION);
			model.setStatusMessage(new StatusMessage(MessageCodes.UN_AUTHORISATION_MSG, MessageCodes.UN_AUTHORISATION_DESC));
			return new ResponseEntity<ExceptionResponseModel>(model, HttpStatus.UNAUTHORIZED);
		}else {
			model.setStatus(MessageCodes.INTERNAL_SERVER_ERROR);
			model.setStatusMessage(new StatusMessage(MessageCodes.INTERNAL_SERVER_ERROR_MSG, MessageCodes.INTERNAL_SERVER_ERROR_DESC));
			return new ResponseEntity<ExceptionResponseModel>(model, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/**
	 * This method used to send bad request response error at global level
	 * @author Manjunath Jakkandi 
	 * @param e
	 * @return
	 * @throws IOExceptionParseException
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseModel> badRequest(MethodArgumentNotValidException e) throws IOException{
		log.error(e.getMessage());
		ExceptionResponseModel model = new ExceptionResponseModel();
		model.setStatus(MessageCodes.BAD_REQUEST);
		model.setStatusMessage(new StatusMessage(MessageCodes.BAD_REQUEST_MSG, e.getMessage()));
        return new ResponseEntity<ExceptionResponseModel>(model, HttpStatus.BAD_REQUEST);
    }
	
	
	/**
	 * This method used to send bad request response error at global level
	 * @author Manjunath Jakkandi
	 * @param e
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ExceptionResponseModel> handleIllegalArgumentException(IllegalArgumentException e) throws IOException {
		log.error(e.getMessage());
		ExceptionResponseModel model = new ExceptionResponseModel();
		model.setStatus(MessageCodes.BAD_REQUEST);
		model.setStatusMessage(new StatusMessage(MessageCodes.BAD_REQUEST_MSG, e.getMessage()));
		return new ResponseEntity<ExceptionResponseModel>(model, HttpStatus.BAD_REQUEST);
	}
	
	
	/**
	 * This method used to send gateway time out error. The time out is defined in application.properties file.
	 * @author Manjunath Jakkandi
	 * @param ce
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler({ConnectTimeoutException.class, WebServiceIOException.class})
    public ResponseEntity<ExceptionResponseModel> invalidResponse(ConnectTimeoutException ce) throws IOException {
		log.error(ce.getMessage());
		ExceptionResponseModel model = new ExceptionResponseModel();
		model.setStatus(MessageCodes.GATEWAY_TIMEOUT);
		model.setStatusMessage(new StatusMessage(MessageCodes.GATEWAY_TIMEOUT_MSG, MessageCodes.GATEWAY_TIMEOUT_DESC));
        return new ResponseEntity<ExceptionResponseModel>(model, HttpStatus.GATEWAY_TIMEOUT);
    }
	
	
}
