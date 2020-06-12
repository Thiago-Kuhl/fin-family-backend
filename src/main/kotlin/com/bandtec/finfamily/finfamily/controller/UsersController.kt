package com.bandtec.finfamily.finfamily.controller

import com.bandtec.finfamily.finfamily.model.GroupParticipants
import com.bandtec.finfamily.finfamily.model.Groups
import com.bandtec.finfamily.finfamily.model.UserUpdate
import com.bandtec.finfamily.finfamily.model.Users
import com.bandtec.finfamily.finfamily.repository.GroupsParticipantRepository
import com.bandtec.finfamily.finfamily.repository.GroupsRepository
import com.bandtec.finfamily.finfamily.repository.GroupsTransactionRepository
import com.bandtec.finfamily.finfamily.repository.UsersRepository
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
        val userTransactions = gtRepository.getAllUserTransactions(userId)
        val userGroupsPart = gpRepository.getUsersGroups(userId)
        var userGroupsOwner = groupsRepository.getUsersGroupsOwner(userId)
        if (userTransactions.isNotEmpty()) {
            gtRepository.deleteAll(userTransactions)
        }
        if (userGroupsPart.isNotEmpty()) {
            userGroupsPart.forEachIndexed { i, it ->
                if (!it.isManager ) {
                    println("Passei por aqui 1")
                    gpRepository.deleteById(it.id)
                } else if (it.isManager && userGroupsOwner[i].groupType == 2) {
                    println("Passei por aqui 2")
                    val allParticipants = gpRepository.getAllParticipants(it.groupId)
                    if (allParticipants.size > 1) {
                        val newManager = gpRepository.getNewManager(userId, it.groupId)
                        newManager.isManager = true
                        gpRepository.save(newManager)
                        userGroupsOwner.forEach { u ->
                            u.groupOwner = newManager.userId
                            groupsRepository.save(u)
                        }
                        gpRepository.deleteById(it.id)
                    }
                }else if(it.isManager){
                    println("Passei por aqui 3")
                    gpRepository.deleteById(it.id)
                    println("Passei por aqui 4")

                    groupsRepository.deleteById(it.groupId)
                    println("Passei por aqui 5")
                }
            }
        }
        println("Passei por aqui 6")
        usersRepository.deleteById(userId)
        return ResponseEntity.status(HttpStatus.OK).body("Sucesso!")
    }
}