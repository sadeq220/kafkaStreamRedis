package com.example.coreMack.service;

import com.example.coreMack.controller.dto.IssueRequest;
import com.example.coreMack.dao.RedisDao;
import com.example.coreMack.dao.RedisTemplateDao;
import com.example.coreMack.model.AccountInfo;
import com.example.coreMack.model.Operation;
import com.example.coreMack.model.TrackAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class MoneyTransfer {//implements SessionCallback {
    private RedisTemplateDao redisTemplateDao;
    private RedisDao redisDao;
    @Autowired
    public MoneyTransfer(RedisTemplateDao redisTemplateDao,RedisDao redisDao){
        this.redisTemplateDao=redisTemplateDao;
        this.redisDao=redisDao;
    }
    public TrackAccount issueRequest(IssueRequest issueRequest){
        AccountInfo account = redisTemplateDao.getAccount(issueRequest.getAccountNo());
        // if issueRequest.operation==WITHDRAW
        return redisTemplateDao.withdrawMoney(issueRequest, account);
    }

    public TrackAccount getTrackAccount(String trackNo){
        Optional<TrackAccount> trackAccount = redisDao.findById(trackNo);
        trackAccount.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,trackNo+" trackNo not exists!"));
        return trackAccount.get();
    }

    public TrackAccount reverseTrackAccount(String trackNo){
        Optional<TrackAccount> optionalTrackAccount = redisDao.findById(trackNo);
        optionalTrackAccount.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,trackNo+" trackNo not exists!"));
        TrackAccount trackAccount = optionalTrackAccount.get();
        trackAccount.setReversed(true);
        AccountInfo accountInfo = redisTemplateDao.getAccount(trackAccount.getAccountNo());
        if (trackAccount.getOperation()==Operation.WITHDRAW) {
            redisTemplateDao.depositMoneyOnReverse(accountInfo, trackAccount);
        }else{
            //redisTemplateDao.withdrawMoney(accountInfo,trackAccount);
        }
        return trackAccount;
    }
}
