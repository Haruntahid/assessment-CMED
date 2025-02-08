package com.cmed.health.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class ResponseWithData {
    private int status;
    private String message;
    private Object data;

    public ResponseWithData(HttpStatus status, String message, Object data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

    public static ResponseEntity<ResponseWithData> successData(HttpStatus status, String message,Object data ) {
        ResponseWithData response = new ResponseWithData(status, message,data);
        return new ResponseEntity<>(response,status);
    }
}
