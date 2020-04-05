package com.bandtec.finfamily.finfamily.controller

import com.bandtec.finfamily.finfamily.model.GroupParticipants
import com.bandtec.finfamily.finfamily.model.Groups
import com.bandtec.finfamily.finfamily.model.UserUpdate
import com.bandtec.finfamily.finfamily.model.Users
import com.bandtec.finfamily.finfamily.repository.GroupsParticipantRepository
import com.bandtec.finfamily.finfamily.repository.GroupsRepository
import com.bandtec.finfamily.finfamily.repository.UsersRepository
import com.bandtec.finfamily.finfamily.security.Encrypt
import com.bandtec.finfamily.finfamily.utils.groupIdGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import java.util.*

@RestController
@RequestMapping("/api/v1/user")
class UsersController {

    @Autowired
    lateinit var usersRepository : UsersRepository

    @Autowired
    lateinit var groupsRepository: GroupsRepository

    @Autowired
    lateinit var groupsParticipantRepository: GroupsParticipantRepository

    val hashpass: Encrypt = Encrypt()

    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())!!

    @PostMapping("login")
    fun loginUser(@ModelAttribute user: Users): ResponseEntity<Users> {
        val searchUser: Users? = usersRepository.loginVerify(user.email)
        if (searchUser != null) {
            val hashedPassword: String? = usersRepository.getPassword(user.email)
            return if (hashpass.customPasswordEncoder()?.matches(user.password, hashedPassword)!!) {
                ResponseEntity.status(HttpStatus.OK).body(searchUser)
            } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

    @PostMapping
    fun createUser(@ModelAttribute user : Users): ResponseEntity<Optional<Users>> {

        var searchUsers: Users? = usersRepository.getUser(user.email, user.cpf)
        var group: Groups
        var groupParticipants: GroupParticipants
        var userId: Int
        var groupId: Int
        return if (searchUsers != null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        } else {
            try{
                user.password = hashpass.customPasswordEncoder()?.encode(user.password)!!
                user.createdAt = currentDate
                usersRepository.save(user)
            }
            catch (err : Exception){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
            try{
                userId = usersRepository.getUserId(user.email)
                var canIPass = false
                while(!canIPass){
                    var groupExternalId = groupIdGenerator()
                    if(groupsRepository.verifyGroupExternalId(groupExternalId) == 0){
                        group = Groups(0, "My Finances", 1, userId, groupExternalId)
                        groupsRepository.save(group)
                        canIPass = true
                    }
                }
            }
            catch (err : Exception){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
            try{
                groupId = groupsRepository.getGroupId(userId)
                groupParticipants = GroupParticipants(0, userId, groupId, true)
                groupsParticipantRepository.save(groupParticipants)
            }
            catch (err : Exception){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }

            ResponseEntity.status(HttpStatus.CREATED).body(usersRepository.findById(userId))
        }
    }

    @PostMapping("update/{id}")
    fun updateUser(@ModelAttribute user : UserUpdate, @PathVariable("id") userId: String): ResponseEntity<Users> {
        var dbUser = usersRepository.getUserById(userId)
        var updatedUser = usersRepository.getUserById(userId)
        try{
            if(user.fullName.isNotEmpty() || user.fullName.isNotBlank()){
                updatedUser.fullName = user.fullName
            }
            if(user.nickname.isNotEmpty() || user.nickname.isNotBlank()){
                updatedUser.nickname = user.nickname
            }
            if(user.email.isNotEmpty() || user.email.isNotBlank()){
                updatedUser.email = user.email
            }
            if(user.basePassword.isNotEmpty() || user.basePassword.isNotBlank()){
                if(hashpass.customPasswordEncoder()?.matches(user.basePassword, dbUser.password)!!){
                    updatedUser.password = hashpass.customPasswordEncoder()?.encode(user.newPassword)!!
                }
                else{
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(dbUser)
                }
            }
            usersRepository.save(updatedUser)
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser)
        }catch (err : Exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dbUser)
        }
    }
}