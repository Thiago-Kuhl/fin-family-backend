package com.bandtec.finfamily.finfamily.controller

import com.bandtec.finfamily.finfamily.model.GroupParticipants
import com.bandtec.finfamily.finfamily.model.Groups
import com.bandtec.finfamily.finfamily.model.Users
import com.bandtec.finfamily.finfamily.repository.*
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
    lateinit var gtRepository: GroupsTransactionRepository

    @Autowired
    lateinit var usersRepository: UsersRepository

    @Autowired
    lateinit var goalsTransRepository: GoalsTransactionsRepository

    @Autowired
    lateinit var goalsRepository: GoalsRepository


    @GetMapping("/members/{externalId}")
    @ApiOperation(value = "Trás os membros de um grupo")
    fun getGroupMembers(@PathVariable("externalId") extId: String): ResponseEntity<List<Users>> {
        return try {
            val groupId = groupsRepository.getIdByExternal(extId)

            val membersId = gpRepository.getGroupMembers(groupId)
            val members = usersRepository.findAllById(membersId).toList()
            ResponseEntity.status(HttpStatus.OK).body(members)
        } catch (err: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @PostMapping("add/members/{userId}/{externalId}")
    @ApiOperation(value = "Adiciona um membro a um grupo")
    fun addMember(@PathVariable("userId") userId: Int, @PathVariable("externalId") extId: String): ResponseEntity<String> {
        return try {
            val groupId = groupsRepository.getIdByExternal(extId)

            val isParticipant = gpRepository.verifyParticipantExistence(userId, groupId)

            return if (isParticipant > 0) {
                ResponseEntity.status(HttpStatus.CONFLICT).build()
            } else {
                val newMember = GroupParticipants(0, userId, groupId, false)
                gpRepository.save(newMember)
                ResponseEntity.status(HttpStatus.OK).body("Sucesso")
            }
        } catch (err: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    //TODO Arrumar a API 'removeMember':
    // - Replicar a lógica da remoção de usuário para remover o usuário do grupo

    @DeleteMapping("remove/members/{userId}/{groupId}")
    @ApiOperation(value = "Remove um membro de um grupo")
    fun removeMember(@PathVariable("userId") userId: Int,
                     @PathVariable("groupId") groupId: Int): ResponseEntity<String> {

        val isOwnerOfGroup = gpRepository.isManager(userId, groupId)
        val groupInfo = groupsRepository.getUserGroupIds(groupId)
        return if (!isOwnerOfGroup) {
            return try {
                val groupTrans = gtRepository.getUserGroupTrans(userId, groupId)
                if (groupTrans.isNotEmpty()) {
                    gtRepository.deleteAll(groupTrans)
                }
                val groupGoalsTrans = goalsTransRepository.getUserGroupTransactions(userId, groupId)
                if (groupGoalsTrans.isNotEmpty()) {
                    goalsTransRepository.deleteAll(groupGoalsTrans)
                }
                val groupMemberId = gpRepository.getGroupMemberId(userId, groupId)
                if (groupMemberId.isNotEmpty()) {
                    gpRepository.deleteAll(groupMemberId)
                }
                ResponseEntity.status(HttpStatus.OK).body("Sucesso!")
            } catch (err: Exception) {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
        } else {
            val groupMembers = gpRepository.getGroupAllMembers(groupId)
            if (groupMembers.size > 1){
                return try{
                    val newManager = gpRepository.getNewManager(userId, groupId)
                    newManager.isManager = true
                    gpRepository.save(newManager)
                    groupInfo.groupOwner = newManager.userId
                    groupsRepository.save(groupInfo)
                    val groupTrans = gtRepository.getUserGroupTrans(userId, groupId)
                    if (groupTrans.isNotEmpty()) {
                        gtRepository.deleteAll(groupTrans)
                    }
                    val groupGoalsTrans = goalsTransRepository.getUserGroupTransactions(userId, groupId)
                    if (groupGoalsTrans.isNotEmpty()) {
                        goalsTransRepository.deleteAll(groupGoalsTrans)
                    }
                    val groupMemberId = gpRepository.getGroupMemberId(userId, groupId)
                    if (groupMemberId.isNotEmpty()) {
                        gpRepository.deleteAll(groupMemberId)
                    }
                    ResponseEntity.status(HttpStatus.OK).body("Sucesso!")
                }catch (err : Exception){
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
                }
            }else {
                val groupTrans = gtRepository.getUserGroupTrans(userId, groupId)
                if (groupTrans.isNotEmpty()) {
                    gtRepository.deleteAll(groupTrans)
                }
                val groupGoalsTrans = goalsTransRepository.getUserGroupTransactions(userId, groupId)
                if (groupGoalsTrans.isNotEmpty()) {
                    goalsTransRepository.deleteAll(groupGoalsTrans)
                }
                val groupMemberId = gpRepository.getGroupMemberId(userId, groupId)
                if (groupMemberId.isNotEmpty()) {
                    gpRepository.deleteAll(groupMemberId)
                }
                val goals = goalsRepository.getGoalsByGroupId(groupId)
                if (goals.isNotEmpty()){
                    goalsRepository.deleteAll(goals)
                }
                groupsRepository.deleteById(groupId)

                ResponseEntity.status(HttpStatus.OK).body("Sucesso!")
            }
        }

    }

}