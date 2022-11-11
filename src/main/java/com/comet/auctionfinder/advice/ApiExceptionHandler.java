package com.comet.auctionfinder.advice;

import com.comet.auctionfinder.dto.ApiException;
import com.comet.auctionfinder.exception.HeartNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiException> argumentNotValidHandler(BindException e) {
        BindingResult result = e.getBindingResult();
        StringBuilder builder = new StringBuilder();
        int size = result.getAllErrors().size();
        List<String> errorField = new ArrayList<>();
        for (ObjectError error : result.getAllErrors()) {
            if (!errorField.contains(error.getCode())) {
                errorField.add(error.getObjectName());
                builder.append(error.getDefaultMessage());
                if (size > 1 && result.getAllErrors().indexOf(error) != size - 1)
                    builder.append("\n");
            }
        }
        ApiException exception = new ApiException(builder.toString(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(exception, exception.getStatus());
    }

    @ExceptionHandler(HeartNotFoundException.class)
    public ResponseEntity<ApiException> heartNotFoundHandler(HeartNotFoundException e) {
        ApiException exception = new ApiException(e.getMessage(), HttpStatus.OK);
        return new ResponseEntity<>(exception, exception.getStatus());
    }

}
