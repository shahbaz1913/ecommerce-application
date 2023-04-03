package com.ecommerce.appliaction.exception;

import org.springframework.http.HttpStatus;

public class EmptyDataException extends Throwable {
    public EmptyDataException (String message, HttpStatus notFound){
        super(message);
    }
}
