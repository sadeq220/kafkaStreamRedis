package com.example.coreMack.util;

import com.example.coreMack.customizedExceptions.MySerializationException;
import com.example.coreMack.model.AccountInfo;
import com.example.coreMack.model.RedisModelCoreBank;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Component
public class CoreModelStringSerde {
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    public CoreModelStringSerde(ObjectMapper objectMapper){
        this.jacksonObjectMapper=objectMapper;
    }

    public String serializeCoreModel(RedisModelCoreBank accountInfo){
        if (accountInfo==null)
            return null;
        try {
            return jacksonObjectMapper.writeValueAsString(accountInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new MySerializationException(accountInfo);
        }
    }
    public AccountInfo deserializeCoreModel(String accountInfoAsString){
        if (accountInfoAsString==null)
            return null;
        if (accountInfoAsString.isBlank())
            return new AccountInfo();
        try {
            return jacksonObjectMapper.readValue(accountInfoAsString.getBytes(StandardCharsets.UTF_8),AccountInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new AccountInfo();
        }
    }
    public Properties convertToFieldValuePair(RedisModelCoreBank coreModel){
        if (coreModel ==null)
            return null;
        Properties properties = jacksonObjectMapper.convertValue(coreModel, Properties.class);
        return properties;
    }
}
