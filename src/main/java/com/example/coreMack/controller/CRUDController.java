package com.example.coreMack.controller;

import com.example.coreMack.controller.dto.AccountDTO;
import com.example.coreMack.dao.RedisTemplateDao;
import com.example.coreMack.model.AccountInfo;
import org.apache.coyote.Response;
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
    @Autowired
    public CRUDController(RedisTemplateDao redisTemplateDao){
        this.redisTemplateDao=redisTemplateDao;
    }
    @PostMapping("/persist/account")
    public ResponseEntity persistAccount(@RequestBody @Valid AccountDTO accountDTO){
        AccountInfo accountInfo = AccountDTO.buildAccount(accountDTO);
        redisTemplateDao.persistAccountInfo(accountInfo);
        return ResponseEntity.ok(accountInfo.getAccountNo());
    }
    @GetMapping("/account/{accountNo}")
    public ResponseEntity getAccount(@PathVariable String accountNo){
        AccountInfo account = redisTemplateDao.getAccount(accountNo);
        return ResponseEntity.ok(account);
    }
}
