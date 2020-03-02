package com.finfamily.controller;

import com.finfamily.repository.AllTransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionTypeController {

    private AllTransactionType allTransactionType;

    @Autowired
    TransactionTypeController(AllTransactionType allTransactionType){this.allTransactionType = allTransactionType;}
}
