package com.example.coreMack.model;

import com.example.coreMack.awareClasses.IoCContainerUtil;
import com.example.coreMack.util.ClientCorrelationIdContextHolder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * using a redis value type 'hash'
 * Redis hashes are an implementation of the hash table data structure .
 * hash includes field-value pairs make it handy to represent objects
 * e.g. trackAccount:ewvqxnudli
 */
@RedisHash("trackAccount")
public class TrackAccount implements RedisModelCoreBank{
    private String accountNo;
    @Id
    private String trackNo;
    private Operation operation;
    private LocalDateTime operationTime;

    public TrackAccount(String accountNo,Operation operation){
        String correlationId = ClientCorrelationIdContextHolder.getCorrelationId();
        if (correlationId==null){
        Random random = IoCContainerUtil.getBean(Random.class);
        final int leftLimit = 97; // letter 'a'
        final int rightLimit = 122; // letter 'z'
        final int targetStringLength = 10;
        correlationId = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        }

        this.operation=operation;
        this.accountNo=accountNo;
        this.trackNo=correlationId;
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
