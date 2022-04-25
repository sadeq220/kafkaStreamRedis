package com.example.coreMack.model;

import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash("trackAccount")
public class TrackAccount {
    private String accountNo;
    private String TrackNo;
    private Operation operation;
    private LocalDateTime operationTime;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getTrackNo() {
        return TrackNo;
    }

    public void setTrackNo(String trackNo) {
        TrackNo = trackNo;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public LocalDateTime getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(LocalDateTime operationTime) {
        this.operationTime = operationTime;
    }
}
