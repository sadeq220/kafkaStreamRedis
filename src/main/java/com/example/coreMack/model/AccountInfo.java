package com.example.coreMack.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(content = JsonInclude.Include.NON_NULL)
public class AccountInfo extends CustomerInfo{
    private String AccountNo;
    private BigDecimal amount;
    private String lastModificationTrackNo;

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String accountNo) {
        AccountNo = accountNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getLastModificationTrackNo() {
        return lastModificationTrackNo;
    }

    public void setLastModificationTrackNo(String lastModificationTrackNo) {
        this.lastModificationTrackNo = lastModificationTrackNo;
    }
}
