package com.example.tccdemo.service;

import com.example.tccdemo.db131.dao.AccountAMapper;
import com.example.tccdemo.db131.model.AccountA;
import com.example.tccdemo.db132.dao.AccountBMapper;
import com.example.tccdemo.db132.model.AccountB;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class AccountService {
    @Resource
    private AccountAMapper accountAMapper;
    @Resource
    private AccountBMapper accountBMapper;

    /**
     * 不能同时配置两个事务管理器，另外一个保证不了，只能提供事务补偿机制
     * rollbackFor 默认是runtimeExcetion,这里定义成Exception
     */
    @Transactional(transactionManager = "tm131",rollbackFor = Exception.class)
    public void transferAccount(){
        AccountA accountA = accountAMapper.selectByPrimaryKey(1);
        accountA.setBalance(accountA.getBalance().subtract(new BigDecimal(200)));
        accountAMapper.updateByPrimaryKey(accountA);

        AccountB accountB = accountBMapper.selectByPrimaryKey(2);
        accountB.setBalance(accountB.getBalance().add(new BigDecimal(200)));
        accountBMapper.updateByPrimaryKey(accountB);

        try{
            int i = 1/0;
        }catch (Exception e){
        // 这里的异常最好是自定义的异常
            try{
                AccountB accountb = accountBMapper.selectByPrimaryKey(2);
                accountb.setBalance(accountb.getBalance().subtract(new BigDecimal(200)));
                accountBMapper.updateByPrimaryKey(accountb);
            }catch (Exception e1){

            }


            throw e;
        }

    }



}
