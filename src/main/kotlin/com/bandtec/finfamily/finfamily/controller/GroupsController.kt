package com.bandtec.finfamily.finfamily.controller

import com.bandtec.finfamily.finfamily.model.GroupParticipants
import com.bandtec.finfamily.finfamily.model.Groups
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
import com.bandtec.finfamily.finfamily.utils.groupIdGenerator

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
        var groupId = 0
        if(repeatedGroup == 0){
            try {
                var canIPass = false
                while(!canIPass){
                    group.externalGroupId = groupIdGenerator()
                    if(groupsRepository.verifyGroupExternalId(group.externalGroupId) == 0){
                        groupsRepository.save(group)
                        canIPass = true
                    }
                }
            }catch (err : Exception){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
            try {
                val group = groupsRepository.getGroupData(group.groupName, group.groupType, group.groupOwner)
                val participants = GroupParticipants(0, group.groupOwner, group.id, true )
                groupId = group.id
                groupsParticipantRepository.save(participants)
            }catch (err : Exception){
                groupsRepository.deleteById(groupId)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(groupsRepository.findById(groupId))
        }else{
            println("Já existe um grupo com o mesmo nome para este usuário!")
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

    }

//    @PostMapping("invite/member")
//    fun sendInvite(@ModelAttribute group: GroupParticipants): ResponseEntity<Optional<GroupParticipants>>{
//
//    }
}