package com.finfamily.controller;

import com.finfamily.repository.AllGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class GroupsController {

    private AllGroups allGroups;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private LocalDateTime now = LocalDateTime.now();

    @Autowired
    public GroupsController(AllGroups allGroups)	{
        this.allGroups = allGroups;
    }


}
