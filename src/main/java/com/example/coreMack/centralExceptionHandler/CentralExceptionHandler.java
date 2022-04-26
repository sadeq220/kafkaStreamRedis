package com.example.coreMack.centralExceptionHandler;

import com.example.coreMack.customizedExceptions.MySerializationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Properties;

@RestControllerAdvice
public class CentralExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MySerializationException.class)
    public ResponseEntity handleSerializeException(MySerializationException mySerializationException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body("this provided AccountInfo can not serialized :"+mySerializationException.getCoreModel());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Properties errors=new Properties();
        for (FieldError fe : fieldErrors){
            String message=fe.getDefaultMessage();
            String fieldName=fe.getField();
            errors.put(fieldName,message);
        }
        return ResponseEntity.status(status).body(errors);
    }
}
