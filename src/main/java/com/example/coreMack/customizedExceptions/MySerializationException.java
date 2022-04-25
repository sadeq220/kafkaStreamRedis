package com.example.coreMack.customizedExceptions;


import com.example.coreMack.model.AccountInfo;

public class MySerializationException extends RuntimeException {
    private AccountInfo accountInfo;
    public MySerializationException(AccountInfo accountInfo){
        super("provided AccountInfo can not serialized");
        this.accountInfo=accountInfo;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }
}
