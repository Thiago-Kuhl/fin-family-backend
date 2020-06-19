package com.bandtec.finfamily.finfamily.controller

import com.bandtec.finfamily.finfamily.model.GroupParticipants
import com.bandtec.finfamily.finfamily.model.Groups
import com.bandtec.finfamily.finfamily.model.UserUpdate
import com.bandtec.finfamily.finfamily.model.Users
import com.bandtec.finfamily.finfamily.repository.*
import com.bandtec.finfamily.finfamily.security.Encrypt
import com.bandtec.finfamily.finfamily.utils.groupIdGenerator
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import java.util.*

@RestController
@RequestMapping("/api/v1/users")
@Api(value = "Usuários", description = "Operações realacionadas ao usuário")
class UsersController {

    @Autowired
    lateinit var usersRepository: UsersRepository

    @Autowired
    lateinit var groupsRepository: GroupsRepository

    @Autowired
    lateinit var gpRepository: GroupsParticipantRepository

    @Autowired
    lateinit var gtRepository: GroupsTransactionRepository

    @Autowired
    lateinit var goalsTransRepository: GoalsTransactionsRepository

    @Autowired
    lateinit var goalsRepository: GoalsRepository

    val hashpass: Encrypt = Encrypt()

    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())!!

    @PostMapping("login")
    @ApiOperation(value = "Realiza o login do usuário")
    fun loginUser(@RequestBody user: Users): ResponseEntity<Users> {
        //println(user.email)
        //println(user.password)
        return try {
            val searchUser: Users? = usersRepository.loginVerify(user.email)
            if (searchUser != null) {
                println("1")
                val hashedPassword: String? = usersRepository.getPassword(user.email)
                println(hashpass.customPasswordEncoder()?.matches(user.password, hashedPassword)!!)

                if (hashpass.customPasswordEncoder()?.matches(user.password, hashedPassword)!!) {
                    println("2")
                    ResponseEntity.status(HttpStatus.OK).body(searchUser)
                } else {
                    println(3)
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
                }

            } else {
                println(4)
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            }
        } catch (err: Exception) {
            println(err)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }


    @PostMapping
    @ApiOperation(value = "Realiza a criação de um usuário")
    fun createUser(@RequestBody user: Users): ResponseEntity<Optional<Users>> {

        var searchUsers: Users? = usersRepository.getUser(user.email, user.cpf)
        var group: Groups
        var groupParticipants: GroupParticipants
        var userId = 0
        var groupId = 0
        return if (searchUsers != null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        } else {
            try {
                user.password = hashpass.customPasswordEncoder()?.encode(user.password)!!
                user.createdAt = currentDate
                usersRepository.save(user)
            } catch (err: Exception) {
                println(err)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
            try {
                userId = usersRepository.getUserId(user.email)
                var canIPass = false
                while (!canIPass) {
                    var groupExternalId = groupIdGenerator()
                    if (groupsRepository.verifyGroupExternalId(groupExternalId) == 0) {
                        group = Groups(0, "My Finances", 1, userId, groupExternalId)
                        groupsRepository.save(group)
                        canIPass = true
                    }
                }
            } catch (err: Exception) {
                usersRepository.deleteById(userId)
                println(err)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
            try {
                groupId = groupsRepository.getGroupIdByOwner(userId)
                groupParticipants = GroupParticipants(0, userId, groupId, true)
                gpRepository.save(groupParticipants)
            } catch (err: Exception) {
                groupsRepository.deleteById(groupId)
                usersRepository.deleteById(userId)
                println(err)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }

            ResponseEntity.status(HttpStatus.CREATED).body(usersRepository.findById(userId))
        }
    }

    @PostMapping("update/{id}")
    @ApiOperation(value = "Realiza a atualização de dados de um usuário")
    fun updateUser(@RequestBody user: UserUpdate, @PathVariable("id") userId: Int): ResponseEntity<Users> {
        var dbUser = usersRepository.getUserById(userId)
        var updatedUser = usersRepository.getUserById(userId)
        try {
            if (user.fullName.isNotEmpty() || user.fullName.isNotBlank()) {
                updatedUser.fullName = user.fullName
            }
            if (user.nickname.isNotEmpty() || user.nickname.isNotBlank()) {
                updatedUser.nickname = user.nickname
            }
            if (user.email.isNotEmpty() || user.email.isNotBlank()) {
                updatedUser.email = user.email
            }
            if (user.basePassword.isNotEmpty() || user.basePassword.isNotBlank()) {
                if (hashpass.customPasswordEncoder()?.matches(user.basePassword, dbUser.password)!!) {
                    updatedUser.password = hashpass.customPasswordEncoder()?.encode(user.newPassword)!!
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(dbUser)
                }
            }
            updatedUser.updatedAt = currentDate
            usersRepository.save(updatedUser)
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser)
        } catch (err: Exception) {
            println(err)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dbUser)
        }
    }

    @GetMapping("name/{userId}")
    @ApiOperation(value = "Trás o nome do nickname do contribuidor")
    fun getUserName(@PathVariable("userId") userId: Int): ResponseEntity<String> {
        val userName = usersRepository.getUserName(userId)
        return if (userName.isNotEmpty()) {
            ResponseEntity.status(HttpStatus.OK).body(userName)

        } else {
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        }
    }

    @GetMapping("{userId}/groups")
    @ApiOperation(value = "Trás todos os grupos de um usuário")
    fun getUserGroups(@PathVariable("userId") userId: Int): ResponseEntity<List<Groups>> {
        val groupIds = gpRepository.getUserGroupIds(userId)
        return try {
            if (groupIds.isEmpty()) {
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            } else {
                val groups = groupsRepository.findAllById(groupIds)
                ResponseEntity.status(HttpStatus.OK).body(groups.toList())
            }
        } catch (err: Exception) {
            println(err)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @DeleteMapping("remove/{userId}")
    @ApiOperation(value = "Remove um usuário do sistema")
    fun removeUsers(@PathVariable("userId") userId: Int): ResponseEntity<String> {
        return try {
            val isParticipantOf = gpRepository.getUsersGroups(userId)
            val isOwnerOfPublic = groupsRepository.getUsersPublicGroupsOwner(userId)

            //Verifica se o usuário participa de algum grupo
            println("Usuário participa de ${isParticipantOf.size} grupos!")
            isParticipantOf.forEachIndexed { i, it ->
                println("Passando pela ${i + 1}ª vez")
                //Verifica se o usuário é manager
                if (it.isManager) {
                    println("Usuário é manager do grupo ${it.groupId}!")
                    //Busca todos os participantes de um grupo
                    val isPublicGroup = groupsRepository.isPublicGroup(it.groupId)
                    if (isPublicGroup) {
                        println("O grupo ${it.groupId} é um grupo público!")
                        val members = gpRepository.getGroupAllMembers(it.groupId)
                        println("O grupo público ${it.groupId} possui ${members.size} membros!")
                        //Verifica se existe mais de 1 participante no grupo
                        if (members.size > 1) {
                            //Define um novo manager
                            val newManager = gpRepository.getNewManager(userId, it.groupId)
                            newManager.isManager = true
                            gpRepository.save(newManager)
                            println("O usuário é manager de ${isOwnerOfPublic.size} grupos públicos!")
                            isOwnerOfPublic.forEach {
                                //altera o owner dos grupos que o user é owner
                                it.groupOwner = newManager.userId
                                groupsRepository.save(it)
                                println("O novo manager do grupo público ${it.id} é o usuário ${newManager.userId}!")
                            }
                            println("Removendo o usuário $userId do grupo ${it.groupId}")
                            gpRepository.deleteById(it.id)
                            val userGoalsTrans = goalsTransRepository.getUserGroupTransactions(userId, it.groupId)
                            println("As metas do grupo público ${it.groupId} possui ${userGoalsTrans.size} transações!")
                            val userGroupTrans = gtRepository.getUserGroupTrans(userId, it.groupId)
                            println("O grupo público ${it.groupId} possui ${userGroupTrans.size} transações!")
                            if (userGroupTrans.isNotEmpty()) {
                                println("Removendo as transações do grupo público ${it.groupId}")
                                gtRepository.deleteAll(userGroupTrans)
                            }
                            if (userGoalsTrans.isNotEmpty()) {
                                println("Removendo as transações das metas do grupo público ${it.groupId}")
                                goalsTransRepository.deleteAll(userGoalsTrans)
                            }
                        } else {
                            gpRepository.deleteById(it.id)
                            val userGroupTrans = gtRepository.getUserGroupTrans(userId, it.groupId)
                            println("O grupo público ${it.groupId} possui ${userGroupTrans.size} transações!")
                            if (userGroupTrans.isNotEmpty()) {
                                println("Removendo as transações do grupo público ${it.groupId}")
                                gtRepository.deleteAll(userGroupTrans)
                            }
                            println("Removendo o grupo ${it.groupId}")
                            groupsRepository.deleteById(it.groupId)
                            val userGoalsTrans = goalsTransRepository.getUserGroupTransactions(userId, it.groupId)
                            println("As metas do grupo público ${it.groupId} possui ${userGoalsTrans.size} transações!")
                            if (userGoalsTrans.isNotEmpty()) {
                                println("Removendo as transações das metas do grupo público ${it.groupId}")
                                goalsTransRepository.deleteAll(userGoalsTrans)
                            }
                            val groupGoals = goalsRepository.getGoalsByGroupId(it.groupId)
                            println("O grupo público ${it.groupId} possui ${groupGoals.size} metas!")
                            if (groupGoals.isNotEmpty()) {
                                println("Removendo as metas do grupo público ${it.groupId}")
                                goalsRepository.deleteAll(groupGoals)
                            }
                        }
                    } else {
                        println("O grupo ${it.groupId} é um grupo privado!")

                        val userGroupTrans = gtRepository.getUserGroupTrans(userId, it.groupId)
                        println("O grupo privado ${it.groupId} possui ${userGroupTrans.size} transações!")
                        if (userGroupTrans.isNotEmpty()) {
                            println("Removendo as transações do grupo privado ${it.groupId}")
                            gtRepository.deleteAll(userGroupTrans)
                        }

                        val userGoalsTrans = goalsTransRepository.getUserGroupTransactions(userId, it.groupId)
                        println("As metas do grupo privado ${it.groupId} possui ${userGoalsTrans.size} transações!")
                        if (userGoalsTrans.isNotEmpty()) {
                            println("Removendo as transações das metas do grupo privado ${it.groupId}")
                            goalsTransRepository.deleteAll(userGoalsTrans)
                        }

                        val groupGoals = goalsRepository.getGoalsByGroupId(it.groupId)
                        println("O grupo privado ${it.groupId} possui ${groupGoals.size} metas!")
                        if (groupGoals.isNotEmpty()) {
                            println("Removendo as metas do grupo privado ${it.groupId}")
                            goalsRepository.deleteAll(groupGoals)
                        }
                        println("Removendo o usuário $userId do grupo ${it.groupId} ")
                        gpRepository.deleteById(it.id)
                        println("Removendo o grupo ${it.groupId}")
                        groupsRepository.deleteById(it.groupId)
                    }
                } else {
                    println("O usuário não é manager do grupo ${it.groupId}, então, pode ser removido sem problemas!")
                    println("Removendo o usuário do grupo ${it.groupId}")
                    gpRepository.deleteById(it.id)
                    val userGroupTrans = gtRepository.getUserGroupTrans(userId, it.groupId)
                    println("O grupo ${it.groupId} possui ${userGroupTrans.size} transações relacionadas as usuário ${userId}!")
                    if (userGroupTrans.isNotEmpty()) {
                        println("Removendo as transações do grupo ${it.groupId}")
                        gtRepository.deleteAll(userGroupTrans)
                    }
                    val userGoalsTrans = goalsTransRepository.getUserGroupTransactions(userId, it.groupId)
                    println("As metas do grupo ${it.groupId} possuem ${userGoalsTrans.size} transações!")
                    if (userGoalsTrans.isNotEmpty()) {
                        println("Removendo as transações das metas do grupo ${it.groupId}")
                        goalsTransRepository.deleteAll(userGoalsTrans)
                    }
                }
            }
            println("O usuário será removido!")
            usersRepository.deleteById(userId)

            ResponseEntity.status(HttpStatus.OK).body("Sucesso!")
        } catch (err: Exception) {
            println(err)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }


}