package com.example.coreMack.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
