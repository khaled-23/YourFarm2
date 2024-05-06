package com.example.yourfarm.Advice;

import com.example.yourfarm.API.ApiException;
import com.example.yourfarm.API.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity ApiException(ApiException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }


    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));


    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity HttpMessageNotReadableException(HttpMessageNotReadableException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));


    }

    @ExceptionHandler(value =  DataIntegrityViolationException.class)
    public ResponseEntity  DataIntegrityViolationException( DataIntegrityViolationException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));


    }

    @ExceptionHandler(value =  MethodArgumentNotValidException.class)
    public ResponseEntity    MethodArgumentNotValidException(   MethodArgumentNotValidException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));


    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity NullPointerException(     NullPointerException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = ClassNotFoundException.class)
    public ResponseEntity ClassNotFoundException(ClassNotFoundException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity NoResourceFoundException(NoResourceFoundException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));

    }
    @ExceptionHandler(value =  MethodArgumentTypeMismatchException.class)
    public ResponseEntity   MethodArgumentTypeMismatchException( MethodArgumentTypeMismatchException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));

    }

    public ResponseEntity IllegalArgumentException(IllegalArgumentException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }
}