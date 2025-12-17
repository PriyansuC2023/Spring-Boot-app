package com.example.ebookapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, String>> handleMaxSize(MaxUploadSizeExceededException ex) {
        Map<String, String> map = new HashMap<>();
        map.put("status", "error");
        map.put("message", "File too large. Max allowed size is " + System.getProperty("spring.servlet.multipart.max-file-size", "configured limit") );
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(map);
    }

    // catch all
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAll(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("status", "error");
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }
}