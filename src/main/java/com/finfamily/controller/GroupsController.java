package com.finfamily.controller;

import com.finfamily.repository.AllGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupsController {

    private AllGroups allGroups;

    @Autowired
    public GroupsController(AllGroups allGroups)	{
        this.allGroups = allGroups;
    }



}
