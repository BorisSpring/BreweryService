package com.brewery.beerservice.exceptions;

import com.brewery.beerservice.model.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> notFoundException(NotFoundException ex, WebRequest request){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDto.builder()
                                                            .errMessage(ex.getMessage())
                                                            .timestamp(LocalDateTime.now())
                                                            .description(request.getDescription(false))
                                                            .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> dataIntegrityViolationException(MethodArgumentNotValidException ex){

        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> dataIntegrityViolationException(ConstraintViolationException ex){

        Map<String,String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(constraintViolation -> {
            errors.put(String.valueOf(constraintViolation.getPropertyPath()), constraintViolation.getMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> dataIntegrityViolationException(Exception ex, WebRequest webRequest){

      return ResponseEntity.internalServerError().body(ErrorResponseDto.builder()
                                                      .timestamp(LocalDateTime.now())
                                                      .errMessage("Internal server error occured!")
                                                      .description(webRequest.getDescription(false))
                                                      .build());
    }
}
