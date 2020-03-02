package com.finfamily.controller;

import com.finfamily.repository.AllGroupWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class GroupWalletController {

    private AllGroupWallet allGroupWallet;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private LocalDateTime now = LocalDateTime.now();

    @Autowired
    GroupWalletController(AllGroupWallet allGroupWallet){this.allGroupWallet = allGroupWallet;}

}
