package com.ecommerce.appliaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleCustomerException(NotFoundException notFoundException) {
        return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AlreadyExists.class)
    public ResponseEntity<String> alreadyPresent(AlreadyExists alreadyexists) {
        return new ResponseEntity<>(alreadyexists.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EmptyDataException.class)
    public ResponseEntity<String> handleEmptyListException(EmptyDataException emptyDataException) {
        return new ResponseEntity<>(emptyDataException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username or password");
    }
    @ExceptionHandler(value = NegativeValueException.class)
    public ResponseEntity<String> handleNegativeValueException(NegativeValueException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
