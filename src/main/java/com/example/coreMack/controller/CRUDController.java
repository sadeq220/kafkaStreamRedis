package com.example.coreMack.controller;

import com.example.coreMack.controller.dto.AccountDTO;
import com.example.coreMack.controller.dto.IssueRequest;
import com.example.coreMack.dao.RedisTemplateDao;
import com.example.coreMack.model.AccountInfo;
import com.example.coreMack.model.TrackAccount;
import com.example.coreMack.service.MoneyTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
/**
 * '@RequestMapping' nominate this class for HandlerMapping(one of core components that DispatcherServlet has)
 *
 * HandlerMapping is a processor mapping, which is used to dispatch requests and establish corresponding relationship between requests and controllers.
 * It is called by the DispatcherServlet
 */
public class CRUDController {
    private RedisTemplateDao redisTemplateDao;
    private MoneyTransfer moneyTransfer;
    @Autowired
    public CRUDController(RedisTemplateDao redisTemplateDao, MoneyTransfer moneyTransfer){
        this.redisTemplateDao=redisTemplateDao;
        this.moneyTransfer = moneyTransfer;
    }
    @PostMapping("/persist/account")
    public ResponseEntity persistAccount(@RequestBody @Valid AccountDTO accountDTO){
        AccountInfo accountInfo = AccountDTO.buildAccount(accountDTO);
        redisTemplateDao.persistAccountInfo(accountInfo);
        return ResponseEntity.ok(accountInfo);
    }
    @GetMapping("/account/{accountNo}")
    public ResponseEntity getAccount(@PathVariable String accountNo){
        AccountInfo account = redisTemplateDao.getAccount(accountNo);
        return ResponseEntity.ok(account);
    }
    @PutMapping("/issueRequest")
    public ResponseEntity issueDocument(@RequestBody @Valid IssueRequest issueRequest){
        TrackAccount trackAccount = moneyTransfer.issueRequest(issueRequest);
        return ResponseEntity.ok(trackAccount);
    }
    @GetMapping("/track/status")
    public ResponseEntity statusTrack(@RequestParam String trackNo){
        TrackAccount trackAccount = moneyTransfer.getTrackAccount(trackNo);
        return ResponseEntity.ok(trackAccount);
    }

}
