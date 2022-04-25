package com.example.coreMack.util;

import com.example.coreMack.model.AccountInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class AccountStringSerde {
    private ObjectMapper jacksonObjectMapper;
    @Autowired
    public AccountStringSerde(ObjectMapper objectMapper){
        this.jacksonObjectMapper=objectMapper;
    }
    public String serializeAccountInfo(AccountInfo accountInfo){
        if (accountInfo==null)
            return null;
        try {
            return jacksonObjectMapper.writeValueAsString(accountInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
    public AccountInfo deserializeAccountInfo(String accountInfoAsString){
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
}
