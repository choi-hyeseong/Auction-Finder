package com.comet.auctionfinder.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiException {

    private String message;
    private HttpStatus status;

}
