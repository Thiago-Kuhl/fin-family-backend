package com.finfamily.controller;

import com.finfamily.domain.ExpenseCategory;
import com.finfamily.repository.AllExpenseCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class ExpenseCategoryController {

    private AllExpenseCategories allExpenseCategories;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private LocalDateTime now = LocalDateTime.now();

    @Autowired
    ExpenseCategoryController(AllExpenseCategories allExpenseCategories) {this.allExpenseCategories = allExpenseCategories;}




}
