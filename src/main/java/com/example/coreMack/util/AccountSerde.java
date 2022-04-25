package com.example.coreMack.util;

import com.example.coreMack.model.AccountInfo;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class AccountSerde implements RedisSerializer<AccountInfo> {
    @Override
    public byte[] serialize(AccountInfo accountInfo) throws SerializationException {
        return new byte[0];
    }

    @Override
    public AccountInfo deserialize(byte[] bytes) throws SerializationException {
        return null;
    }

    @Override
    public boolean canSerialize(Class<?> type) {
        return false;
    }

    @Override
    public Class<?> getTargetType() {
        return null;
    }
}
