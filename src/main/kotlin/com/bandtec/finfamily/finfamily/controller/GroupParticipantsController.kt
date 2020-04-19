package com.bandtec.finfamily.finfamily.controller

import com.bandtec.finfamily.finfamily.model.GroupParticipants
import com.bandtec.finfamily.finfamily.model.Groups
import com.bandtec.finfamily.finfamily.repository.GroupsParticipantRepository
import com.bandtec.finfamily.finfamily.repository.GroupsRepository
import com.bandtec.finfamily.finfamily.repository.GroupsTransactionRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/group/participants")
@Api(value = "Membros de Grupos", description = "Operações realacionadas ao membros de grupos")
class GroupParticipantsController {

    @Autowired
    lateinit var groupsParticipantRepository: GroupsParticipantRepository

    @Autowired
    lateinit var groupsRepository: GroupsRepository

    @Autowired
    lateinit var groupsTransactionRepository: GroupsTransactionRepository


    @PostMapping("add/{userId}/{externalId}")
    @ApiOperation(value = "Adiciona um membro a um grupo")
    fun addMember(@ModelAttribute groupParticipant: GroupParticipants, @PathVariable("userId") userId: Int,
                  @PathVariable("externalId") externalId: String): ResponseEntity<GroupParticipants> {

        return try {
            val groupId = groupsRepository.getIdByExternal(externalId)

            if (groupsParticipantRepository.verifyParticipantExistence(userId, groupId) == 0) {
                val member = GroupParticipants(0, userId, groupId, false)
                groupsParticipantRepository.save(member)
                ResponseEntity.status(HttpStatus.OK).body(member)
            }
            else {
                ResponseEntity.status(HttpStatus.CONFLICT).build()
            }
        } catch (err: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @PostMapping("remove/{userId}/{groupId}")
    @ApiOperation(value = "Remove um membro de um grupo")
    fun removeMember(@PathVariable("userId") userId: Int,
                     @PathVariable("groupId") groupId: Int): ResponseEntity<String> {

        val member = groupsParticipantRepository.getGroupMember(userId, groupId)
        val transactions = groupsTransactionRepository.getUserTransactions(userId, groupId)


        try {
            if(transactions.isNotEmpty()){
                groupsTransactionRepository.deleteAll(transactions)
            }
        }
        catch (err: Exception) {
            println(err)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

        if (!groupsParticipantRepository.isManager(userId, groupId)) {
            return try {
                groupsParticipantRepository.deleteAll(member)
                ResponseEntity.status(HttpStatus.OK).build()
            } catch (err: Exception) {
                groupsTransactionRepository.saveAll(transactions)
                println(err)
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
        }
        else {
            try {
                if (groupsParticipantRepository.getAllManagers(groupId) > 1) {
                    return try {
                        groupsParticipantRepository.deleteAll(member)
                        ResponseEntity.status(HttpStatus.OK).build()
                    } catch (err: Exception) {
                        groupsTransactionRepository.saveAll(transactions)
                        println(err)
                        ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
                    }
                }
                else {
                    return try {
                        val newManager = groupsParticipantRepository.getNewManager(userId, groupId)
                        newManager.isManager = true
                        groupsParticipantRepository.save(newManager)
                        groupsParticipantRepository.deleteAll(member)
                        ResponseEntity.status(HttpStatus.OK).build()
                    } catch (err: Exception) {
                        groupsTransactionRepository.saveAll(transactions)
                        println(err)
                        ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
                    }
                }
            }
            catch (err: Exception) {
                println(err)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
        }
    }

}