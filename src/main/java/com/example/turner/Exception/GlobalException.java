package com.example.turner.Exception;

import com.example.turner.DTO.ErrorByMe;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorByMe> notFound(EntityNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorByMe(e.getMessage()));
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorByMe>illegalState(IllegalStateException e){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorByMe(e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorByMe> illegalArgument(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorByMe(e.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorByMe>otherExceptions(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorByMe("Error del sistema: " +
                e.getMessage()));
    }
}
