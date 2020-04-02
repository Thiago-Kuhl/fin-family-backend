package com.bandtec.finfamily.finfamily.controller

import com.bandtec.finfamily.finfamily.model.GroupParticipants
import com.bandtec.finfamily.finfamily.model.Groups
import com.bandtec.finfamily.finfamily.model.Users
import com.bandtec.finfamily.finfamily.repository.GroupsParticipantRepository
import com.bandtec.finfamily.finfamily.repository.GroupsRepository
import com.bandtec.finfamily.finfamily.repository.UsersRepository
import com.bandtec.finfamily.finfamily.security.Encrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
                group = Groups(0, "My Finances", 1, userId)
                groupsRepository.save(group)
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
}