package com.bandtec.finfamily.finfamily.controller

import com.bandtec.finfamily.finfamily.model.GroupParticipants
import com.bandtec.finfamily.finfamily.model.Users
import com.bandtec.finfamily.finfamily.repository.GroupsParticipantRepository
import com.bandtec.finfamily.finfamily.repository.GroupsRepository
import com.bandtec.finfamily.finfamily.repository.GroupsTransactionRepository
import com.bandtec.finfamily.finfamily.repository.UsersRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/groups/participants")
@Api(value = "Membros de Grupos", description = "Operações realacionadas ao membros de grupos")
class GroupParticipantsController {

    @Autowired
    lateinit var gpRepository: GroupsParticipantRepository

    @Autowired
    lateinit var groupsRepository: GroupsRepository

    @Autowired
    lateinit var groupsTransactionRepository: GroupsTransactionRepository

    @Autowired
    lateinit var usersRepository: UsersRepository


    @GetMapping("/members/{externalId}")
    @ApiOperation(value = "Trás os membros de um grupo")
    fun getGroupMembers(@PathVariable("externalId") extId : String) : ResponseEntity<List<Users>>{
        return try{
            val groupId = groupsRepository.getIdByExternal(extId)

            val membersId = gpRepository.getGroupMembers(groupId)
            val members = usersRepository.findAllById(membersId).toList()
            ResponseEntity.status(HttpStatus.OK).body(members)
        }catch (err : Exception){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @PostMapping("add/members/{userId}/{externalId}")
    @ApiOperation(value = "Adiciona um membro a um grupo")
    fun addMember(@PathVariable("userId") userId: Int, @PathVariable("externalId") extId: String): ResponseEntity<GroupParticipants> {
        return try {
            val groupId = groupsRepository.getIdByExternal(extId)

            val isParticipant = gpRepository.verifyParticipantExistence(userId, groupId)

            return if (isParticipant > 0){
                ResponseEntity.status(HttpStatus.CONFLICT).build()
            } else {
                val newMember = GroupParticipants(0, userId, groupId, false)
                gpRepository.save(newMember)
                ResponseEntity.status(HttpStatus.OK).build()
            }
        }catch (err : Exception){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @PostMapping("remove/{userId}/{groupId}")
    @ApiOperation(value = "Remove um membro de um grupo")
    fun removeMember(@PathVariable("userId") userId: Int,
                     @PathVariable("groupId") groupId: Int): ResponseEntity<String> {

        val member = gpRepository.getGroupMember(userId, groupId)
        val transactions = groupsTransactionRepository.getUserTransactions(userId, groupId)


        try {
            if (transactions.isNotEmpty()) {
                groupsTransactionRepository.deleteAll(transactions)
            }
        } catch (err: Exception) {
            println(err)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

        if (!gpRepository.isManager(userId, groupId)) {
            return try {
                gpRepository.deleteAll(member)
                ResponseEntity.status(HttpStatus.OK).build()
            } catch (err: Exception) {
                groupsTransactionRepository.saveAll(transactions)
                println(err)
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
        } else {
            try {
                if (gpRepository.getAllManagers(groupId) > 1) {
                    return try {
                        gpRepository.deleteAll(member)
                        ResponseEntity.status(HttpStatus.OK).build()
                    } catch (err: Exception) {
                        groupsTransactionRepository.saveAll(transactions)
                        println(err)
                        ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
                    }
                } else {
                    return try {
                        val newManager = gpRepository.getNewManager(userId, groupId)
                        newManager.isManager = true
                        gpRepository.save(newManager)
                        gpRepository.deleteAll(member)
                        ResponseEntity.status(HttpStatus.OK).build()
                    } catch (err: Exception) {
                        groupsTransactionRepository.saveAll(transactions)
                        println(err)
                        ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
                    }
                }
            } catch (err: Exception) {
                println(err)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
        }
    }

}