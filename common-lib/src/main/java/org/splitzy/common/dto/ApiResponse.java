package org.splitzy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String error;
    private LocalDateTime timeStamp;

    public static <T> ApiResponse<T> success(T data){
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message){
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String error){
        return ApiResponse.<T>builder()
                .success(false)
                .error(error)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String error, String message){
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .error(error)
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
