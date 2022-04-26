package com.example.coreMack.service;

import com.example.coreMack.controller.dto.IssueRequest;
import com.example.coreMack.dao.RedisTemplateDao;
import com.example.coreMack.model.AccountInfo;
import com.example.coreMack.model.TrackAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class WithdrawMoney {//implements SessionCallback {
    private RedisTemplateDao redisTemplateDao;
    @Autowired
    public WithdrawMoney(RedisTemplateDao redisTemplateDao){
        this.redisTemplateDao=redisTemplateDao;
    }
    public TrackAccount issueRequest(IssueRequest issueRequest){
        AccountInfo account = redisTemplateDao.getAccount(issueRequest.getAccountNo());
        return redisTemplateDao.issueRequest(issueRequest, account);
    }
}
