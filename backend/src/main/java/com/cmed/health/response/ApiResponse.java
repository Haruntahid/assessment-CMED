package com.cmed.health.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private int status;
    private String message;
    private String token;

    public static ResponseEntity<ApiResponse> success(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(ApiResponse.builder()
                        .status(status.value())
                        .message(message)
                        .build());
    }

    public static ResponseEntity<ApiResponse> successWithToken(HttpStatus status, String message, String token) {
        return ResponseEntity.status(status)
                .body(ApiResponse.builder()
                        .status(status.value())
                        .message(message)
                        .token(token)
                        .build());
    }
}
