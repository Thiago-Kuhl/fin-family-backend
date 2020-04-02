package com.bandtec.finfamily.finfamily.controller

import com.bandtec.finfamily.finfamily.repository.GroupsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/group")
class GroupsController {

    @Autowired
    lateinit var groupsRepository: GroupsRepository
}