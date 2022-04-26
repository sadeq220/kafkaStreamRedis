package com.example.coreMack.customizedExceptions;


import com.example.coreMack.model.RedisModelCoreBank;

public class MySerializationException extends RuntimeException {
    private RedisModelCoreBank accountInfo;
    public MySerializationException(RedisModelCoreBank accountInfo){
        super("provided AccountInfo can not serialized");
        this.accountInfo=accountInfo;
    }

    public RedisModelCoreBank getCoreModel() {
        return accountInfo;
    }
}
