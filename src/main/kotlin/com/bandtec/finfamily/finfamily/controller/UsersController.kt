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
    val currentDate = sdf.format(Date())

    @PostMapping("login")
    fun loginUser(@ModelAttribute user: Users): ResponseEntity<Users?>? {
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
    fun createUser(@ModelAttribute user : Users): ResponseEntity<Users?>?{

        var searchUsers : List<Users?>? = usersRepository.verifyExistence(user.email, user.cpf)
        var group : Groups

        return if (searchUsers?.isNotEmpty()!!){
            ResponseEntity.badRequest().build()
        }
        else{
//            try {
                user.password = hashpass.customPasswordEncoder()?.encode(user.password)!!
                user.createdAt = currentDate;
                usersRepository.save(user);
//            }
//            catch (err : Exception){
//                return ResponseEntity.badRequest().build()
//            }
//            try {
                group = Groups(0, "My Finances", 1, usersRepository.getUserId(user.cpf, user.email))

                groupsRepository.save(group)

//            }
//            catch (err : Exception) {
//                return ResponseEntity.badRequest().build()
//            }
//            try {
                val groupParticipants = GroupParticipants(0, usersRepository.getUserId(user.cpf, user.email),
                        groupsRepository.getGroupId(group.groupOwner), true)

                groupsParticipantRepository.save(groupParticipants)
//               return ResponseEntity.ok().build()
//            }
//            catch (err : java.lang.Exception) {
//                groupsRepository.removeGroup(usersRepository.getUserId(user.cpf, user.email))
//                usersRepository.removeUser(user.cpf, user.email)
//            }
            val loginUser : Users? = usersRepository.loginVerify(user.email)
            ResponseEntity.status(HttpStatus.OK).body(loginUser)
        }


    }

}