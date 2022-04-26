package com.example.coreMack.service;

import com.example.coreMack.controller.dto.IssueRequest;
import com.example.coreMack.dao.RedisTemplateDao;
import com.example.coreMack.model.AccountInfo;
import com.example.coreMack.model.TrackAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyTransfer {//implements SessionCallback {
    private RedisTemplateDao redisTemplateDao;
    @Autowired
    public MoneyTransfer(RedisTemplateDao redisTemplateDao){
        this.redisTemplateDao=redisTemplateDao;
    }
    public TrackAccount issueRequest(IssueRequest issueRequest){
        AccountInfo account = redisTemplateDao.getAccount(issueRequest.getAccountNo());
        // if issueRequest.operation==WITHDRAW
        return redisTemplateDao.withdrawMoney(issueRequest, account);
    }
}
