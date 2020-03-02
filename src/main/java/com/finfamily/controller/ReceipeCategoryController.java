package com.finfamily.controller;

import com.finfamily.domain.ReceipeCategory;
import com.finfamily.repository.AllReceipeCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class ReceipeCategoryController {

    private AllReceipeCategories allReceipeCategories;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private LocalDateTime now = LocalDateTime.now();

    @Autowired
    ReceipeCategoryController(AllReceipeCategories allReceipeCategories){this.allReceipeCategories = allReceipeCategories;}

}
