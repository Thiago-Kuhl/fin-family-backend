package com.finfamily.controller;

import com.finfamily.repository.AllGroupParticipants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupParticipantsController {

    private AllGroupParticipants allGroupParticipants;

    @Autowired
    GroupParticipantsController(AllGroupParticipants allGroupParticipants){this.allGroupParticipants = allGroupParticipants;}
}
