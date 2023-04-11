package com.ecommerce.appliaction.advice;

import com.ecommerce.appliaction.exception.AlreadyExists;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NegativeValueException;
import com.ecommerce.appliaction.exception.NoSuchElementFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

        @ExceptionHandler(value = NoSuchElementFoundException.class)
        @ResponseStatus(value = HttpStatus.NOT_FOUND)
        public ResponseEntity<String> handleCustomerException(NoSuchElementFoundException noSuchElementFoundException) {
            return new ResponseEntity<>(noSuchElementFoundException.getMessage(), HttpStatus.NOT_FOUND);
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
