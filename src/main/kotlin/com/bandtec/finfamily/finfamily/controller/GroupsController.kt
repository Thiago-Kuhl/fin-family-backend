package com.bandtec.finfamily.finfamily.controller

import com.bandtec.finfamily.finfamily.model.GroupParticipants
import com.bandtec.finfamily.finfamily.model.Groups
import com.bandtec.finfamily.finfamily.repository.GroupsParticipantRepository
import com.bandtec.finfamily.finfamily.repository.GroupsRepository
import com.bandtec.finfamily.finfamily.repository.GroupsTransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*
import com.bandtec.finfamily.finfamily.utils.groupIdGenerator
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/group")
class GroupsController {

    @Autowired
    lateinit var groupsRepository: GroupsRepository

    @Autowired
    lateinit var groupsParticipantRepository: GroupsParticipantRepository

    @Autowired
    lateinit var groupsTransactionRepository: GroupsTransactionRepository

    @PostMapping("create")
    fun createGroup(@ModelAttribute group: Groups): ResponseEntity<Optional<Groups>> {
        val repeatedGroup = groupsRepository.verifyGroupToUser(group.groupName, group.groupType, group.groupOwner)
        var groupId = 0
        if (repeatedGroup == 0) {
            try {
                var canIPass = false
                while (!canIPass) {
                    group.externalGroupId = groupIdGenerator()
                    if (groupsRepository.verifyGroupExternalId(group.externalGroupId) == 0) {
                        groupsRepository.save(group)
                        canIPass = true
                    }
                }
            } catch (err: Exception) {
                println(err)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
            try {
                val group = groupsRepository.getGroupData(group.groupName, group.groupType, group.groupOwner)
                val participants = GroupParticipants(0, group.groupOwner, group.id, true)
                groupId = group.id
                groupsParticipantRepository.save(participants)
            } catch (err: Exception) {
                println(err)
                groupsRepository.deleteById(groupId)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(groupsRepository.findById(groupId))
        } else {
            println("Já existe um grupo com o mesmo nome para este usuário!")
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

    }

    @PostMapping("remove/{groupId}/{externalId}")
    fun removePublicGroup(@PathVariable("groupId") groupId: Int,
                    @PathVariable("externalId") external_id: String): ResponseEntity<Groups> {
        val members = groupsParticipantRepository.getGroupAllMembers(groupId)
        val transactions = groupsTransactionRepository.getGroupTransactions(groupId)

        try {
            if(transactions.isNotEmpty()){
                groupsTransactionRepository.deleteAll(transactions)
            }
        } catch (err: Exception) {
            println(err)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
        try {
                groupsParticipantRepository.deleteAll(members)
        } catch (err: Exception) {
            groupsTransactionRepository.saveAll(transactions)
            println(err)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
        try {
            groupsRepository.deleteById(groupId)
        } catch (err: Exception) {
            groupsParticipantRepository.saveAll(members)
            groupsTransactionRepository.saveAll(transactions)
            println(err)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

        return ResponseEntity.status(HttpStatus.OK).build()
    }
}