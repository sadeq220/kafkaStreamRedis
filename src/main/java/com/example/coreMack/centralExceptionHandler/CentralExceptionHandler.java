package com.example.coreMack.centralExceptionHandler;

import com.example.coreMack.customizedExceptions.MySerializationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CentralExceptionHandler {
    @ExceptionHandler(MySerializationException.class)
    public ResponseEntity handleSerializeException(MySerializationException mySerializationException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body("this provided AccountInfo can not serialized :"+mySerializationException.getAccountInfo());
    }
}
