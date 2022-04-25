package com.example.coreMack.controller.dto;

import com.example.coreMack.model.AccountInfo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * javax Bean validation API
 *  if client violate any constraint these API will throw 'MethodArgumentNotValidException'
 */
public class AccountDTO {
    @NotEmpty
    private String accountNo;
    @NotEmpty
    private String username;
    private String name;
    private String lastName;
    @Pattern(regexp = "^\\+[0-9]{11}")
    private String mobileNo;


    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public static AccountInfo buildAccount(AccountDTO accountDTO){
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountNo(accountDTO.accountNo);
        accountInfo.setActualName(accountDTO.name);
        accountInfo.setActualLastName(accountDTO.lastName);
        accountInfo.setUserName(accountDTO.username);
        accountInfo.setMobileNO(accountDTO.mobileNo);
        return accountInfo;
    }
}
