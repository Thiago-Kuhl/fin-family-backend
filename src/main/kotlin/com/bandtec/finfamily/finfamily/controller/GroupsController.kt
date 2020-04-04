package com.bandtec.finfamily.finfamily.controller

import com.bandtec.finfamily.finfamily.model.GroupParticipants
import com.bandtec.finfamily.finfamily.model.Groups
import com.bandtec.finfamily.finfamily.model.Users
import com.bandtec.finfamily.finfamily.repository.GroupsParticipantRepository
import com.bandtec.finfamily.finfamily.repository.GroupsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("/api/v1/group")
class GroupsController {

    @Autowired
    lateinit var groupsRepository: GroupsRepository

    @Autowired
    lateinit var groupsParticipantRepository: GroupsParticipantRepository

    @PostMapping("create")
    fun createGroup(@ModelAttribute group: Groups): ResponseEntity<Optional<Groups>> {
        val repeatedGroup = groupsRepository.verifyGroupToUser(group.groupName, group.groupType, group.groupOwner)
        var groupId : Int
        if(repeatedGroup == 0){
            try {
                groupsRepository.save(group)
            }catch (err : Exception){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
            try {
                val group = groupsRepository.getGroupData(group.groupName, group.groupType, group.groupOwner)
                val participants = GroupParticipants(0, group.groupOwner, group.id, true )
                groupsParticipantRepository.save(participants)
                groupId = group.id
            }catch (err : Exception){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
            return ResponseEntity.status(HttpStatus.OK).body(groupsRepository.findById(groupId))
        }else{
            println("Já existe um grupo com o mesmo nome para este usuário")
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

    }
}