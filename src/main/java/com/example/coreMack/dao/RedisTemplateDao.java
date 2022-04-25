package com.example.coreMack.dao;

import com.example.coreMack.model.AccountInfo;
import com.example.coreMack.util.AccountStringSerde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
/**
 * '@Repository' for exception translations
 */
public class RedisTemplateDao {

    private StringRedisTemplate redisTemplate;
    private AccountStringSerde accountStringSerde;

    @Autowired
    public RedisTemplateDao(StringRedisTemplate redisTemplate,
                            AccountStringSerde accountStringSerde){
        this.accountStringSerde=accountStringSerde;
        this.redisTemplate=redisTemplate;
    }

    /**
     * opsForValue() provides methods to execute operations performed on simple values (or Strings in Redis terminology)
     */
    public void persistAccountInfo(AccountInfo accountInfo){
        Assert.notNull(accountInfo.getAccountNo(),"accountNo must not be null");
        String accountInfoAsString = accountStringSerde.serializeAccountInfo(accountInfo);
        redisTemplate.opsForValue().set(accountInfo.getAccountNo(),accountInfoAsString);
    }
}
