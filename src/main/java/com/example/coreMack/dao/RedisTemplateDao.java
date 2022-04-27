package com.example.coreMack.dao;

import com.example.coreMack.controller.dto.IssueRequest;
import com.example.coreMack.model.AccountInfo;
import com.example.coreMack.model.Operation;
import com.example.coreMack.model.TrackAccount;
import com.example.coreMack.util.CoreModelStringSerde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.util.Properties;

@Repository
/**
 * '@Repository' for exception translations
 */
public class RedisTemplateDao {

    private StringRedisTemplate redisTemplate;
    private CoreModelStringSerde coreModelStringSerde;
    private ValueOperations<String,String> valueOperations;
    private HashOperations hashOperations;

    @Autowired
    /**
     * To support various operations on different datatypes,
     * RedisTemplate provides operation classes like
     * ValueOperations, ListOperations, SetOperations, HashOperations, StreamOperations, etc.
     */
    public RedisTemplateDao(StringRedisTemplate redisTemplate,
                            CoreModelStringSerde coreModelStringSerde){
        this.coreModelStringSerde = coreModelStringSerde;
        this.redisTemplate=redisTemplate;
        valueOperations= redisTemplate.opsForValue();
        this.hashOperations= redisTemplate.opsForHash();
    }

    /**
     * opsForValue() provides methods to execute operations performed on simple values (or Strings in Redis terminology)
     *
     * Redis Hashes can hold an n number of key-value pairs and are designed to use less memory, making it a great way for storing objects in-memory
     * Hash map name,Hash key ,value
     */
    @Transactional
    public void persistAccountInfo(AccountInfo accountInfo){
        Assert.notNull(accountInfo.getAccountNo(),"accountNo must not be null");
        String accountInfoAsString = coreModelStringSerde.serializeCoreModel(accountInfo);
        valueOperations.set(accountInfo.getAccountNo(),accountInfoAsString);
    }

    public AccountInfo getAccount(String accountNo){
        Assert.notNull(accountNo,"accountNo must not be null");
        String accountAsString = valueOperations.get(accountNo);
        if (accountAsString==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,accountNo+" doesn't exist !");
        return coreModelStringSerde.deserializeCoreModel(accountAsString);
    }

    @Transactional
    /**
     * transaction begins with redisTemplate.multi()
     * If the transaction finishes without errors, EXEC is called.
     * Otherwise DISCARD is called.
     * Once in MULTI, RedisConnection queues write operations.
     *
     * All readonly operations, such as KEYS, are piped to a fresh (non-thread-bound) RedisConnection.
     */
    public TrackAccount withdrawMoney(IssueRequest issueRequest, AccountInfo accountInfo){

        TrackAccount trackAccount = new TrackAccount(accountInfo.getAccountNo(), issueRequest.getOperation());
        // redisDao.save(trackAccount); not support transaction
        Properties properties = coreModelStringSerde.convertToFieldValuePair(trackAccount);
        hashOperations.putAll("trackAccount:"+trackAccount.getTrackNo(),properties);

        // assume issueRequest always have a Withdraw operation
        // update account info
        accountInfo.setAmount(accountInfo.getAmount().subtract(issueRequest.getAmount()));
        accountInfo.setLastModificationTrackNo(trackAccount.getTrackNo());
        String accountAsSerde = coreModelStringSerde.serializeCoreModel(accountInfo);
        valueOperations.set(accountInfo.getAccountNo(),accountAsSerde);

        return trackAccount;
    }
}
