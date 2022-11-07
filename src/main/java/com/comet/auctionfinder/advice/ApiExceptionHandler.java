package com.comet.auctionfinder.advice;

import com.comet.auctionfinder.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiException> argumentNotValidHandler(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        StringBuilder builder = new StringBuilder();
        for (FieldError error : result.getFieldErrors()) {
            builder.append(error.getDefaultMessage());
            builder.append("\n");
        }
        ApiException exception = new ApiException(builder.toString(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(exception, exception.getStatus());
    }
}
