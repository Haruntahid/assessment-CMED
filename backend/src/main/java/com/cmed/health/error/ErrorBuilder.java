package com.cmed.health.error;

import com.cmed.health.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ErrorBuilder {
        public ResponseEntity<ErrorResponse> buildErrorResponse(Exception exception, HttpStatus httpStatus) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setStatus(httpStatus.value());
            errorResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(errorResponse, httpStatus);
        }

}
