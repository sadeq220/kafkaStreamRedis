package com.example.coreMack.model;

import com.example.coreMack.awareClasses.IoCContainerUtil;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.Random;

@RedisHash("trackAccount")
public class TrackAccount implements RedisModelCoreBank{
    private String accountNo;
    private String trackNo;
    private Operation operation;
    private LocalDateTime operationTime;

    public TrackAccount(String accountNo,Operation operation){
        Random random = IoCContainerUtil.getBean(Random.class);
        final int leftLimit = 97; // letter 'a'
        final int rightLimit = 122; // letter 'z'
        final int targetStringLength = 10;
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        this.operation=operation;
        this.accountNo=accountNo;
        this.trackNo=generatedString;
        this.operationTime=LocalDateTime.now();
    }
    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo;
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
