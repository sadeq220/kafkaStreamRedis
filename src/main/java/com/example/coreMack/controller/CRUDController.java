package com.example.coreMack.controller;

import com.example.coreMack.dao.RedisTemplateDao;
import com.example.coreMack.model.AccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/persist/Account")
    public ResponseEntity persistAccount(@RequestBody AccountInfo accountInfo){
        redisTemplateDao.persistAccountInfo(accountInfo);
        return ResponseEntity.ok(accountInfo.getAccountNo());
    }
}
