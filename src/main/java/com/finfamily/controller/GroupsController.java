package com.finfamily.controller;

import com.finfamily.domain.AllGroups;
import com.finfamily.domain.AllUsers;
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
