package com.coffee.tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> notFound(ResourceNotFoundException ex){ return error(HttpStatus.NOT_FOUND, ex.getMessage()); }
    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<?> email(EmailAlreadyUsedException ex){ return error(HttpStatus.CONFLICT, ex.getMessage()); }
    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<?> unauthorized(UnauthorizedOperationException ex){ return error(HttpStatus.FORBIDDEN, ex.getMessage()); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validation(MethodArgumentNotValidException ex){ Map<String,Object> body=new HashMap<>(); body.put("timestamp", Instant.now().toString()); body.put("status",400); Map<String,String> errs=new HashMap<>(); for(FieldError fe: ex.getBindingResult().getFieldErrors()){ errs.put(fe.getField(), fe.getDefaultMessage()); } body.put("errors", errs); return ResponseEntity.badRequest().body(body);} 
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegal(IllegalArgumentException ex){ return error(HttpStatus.BAD_REQUEST, ex.getMessage()); }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> generic(Exception ex){
        // Se for recurso estático ausente (ou path inexistente) devolver 404 em vez de 500
        String raw = ex.getMessage();
        if(ex instanceof NoResourceFoundException || (raw!=null && raw.startsWith("No static resource"))){
            return error(HttpStatus.NOT_FOUND, "Not found");
        }
        String msg = raw==null? "Erro interno" : raw;
        return error(HttpStatus.INTERNAL_SERVER_ERROR, msg);
    }
    private ResponseEntity<Map<String,Object>> error(HttpStatus status, String msg){ Map<String,Object> body=new HashMap<>(); body.put("timestamp", Instant.now().toString()); body.put("status", status.value()); body.put("message", msg); return ResponseEntity.status(status).body(body);} }
