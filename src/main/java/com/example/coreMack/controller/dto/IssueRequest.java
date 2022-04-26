package com.example.coreMack.controller.dto;

import com.example.coreMack.model.Operation;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class IssueRequest {
    @NotEmpty
    private String accountNo;
    @DecimalMin("10000")
    private BigDecimal amount;
    private Operation operation;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
