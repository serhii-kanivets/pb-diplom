package com.pb.kanivets.bki.spring.web.controllers;

import com.pb.kanivets.bki.core.exceptions.BusinessException;
import com.pb.kanivets.bki.core.wrappers.ExceptionWrapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionWrapper handleError(BusinessException t){
        Logger.getLogger(ErrorHandler.class.getName()).log(Level.SEVERE,t.getMessage(), t);
        return new ExceptionWrapper(t);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ExceptionWrapper handleError(Throwable t){
        Logger.getLogger(ErrorHandler.class.getName()).log(Level.SEVERE,t.getMessage(), t);
        return new ExceptionWrapper(t);
    }
    
}
