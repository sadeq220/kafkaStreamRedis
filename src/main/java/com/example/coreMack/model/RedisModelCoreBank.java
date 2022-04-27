package com.example.coreMack.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,property = "_class",defaultImpl = AccountInfo.class)
/**
 * add property "_class" on serialization to json to comply with
 * Redis Repository
 */
public interface RedisModelCoreBank {
    String getAccountNo();
    void setAccountNo(String accountNo);
}
