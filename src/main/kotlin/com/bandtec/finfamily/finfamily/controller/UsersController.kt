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
    fun createUser(@ModelAttribute user : Users): ResponseEntity<Optional<Users>> {

        var searchUser: Users? = usersRepository.loginVerify(user.email)
        var userId : Int
        var groupId : Int
        var group : Groups
        var groupParticipants : GroupParticipants

        return if (searchUser == null) {

            user.password = hashpass.customPasswordEncoder()?.encode(user.password)!!
            user.createdAt = currentDate
            usersRepository.save(user)

            userId = usersRepository.getUserId(user.email)

            group = Groups(0, "My Finances", 1, userId)

            groupsRepository.save(group)

            groupId = groupsRepository.getGroupId(userId)

            groupParticipants = GroupParticipants(0, userId, groupId, true)

            groupsParticipantRepository.save(groupParticipants)

            println("ooooi")

            val userReturn : Optional<Users> = usersRepository.findById(userId)
            ResponseEntity.status(HttpStatus.CREATED).body(userReturn)

        }else{
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

//        usersRepository.loginVerify(user.email)

//        user.password = hashpass.customPasswordEncoder()?.encode(user.password)!!
//            user.createdAt = currentDate;
//            usersRepository.save(user);
//
//        val userId =  9
//        val groupId = 9
//        val group = Groups(0, "My Finances", 1, userId)
//
//            groupsRepository.save(group)
//
//            val groupParticipants = GroupParticipants(0, userId,
//                    groupId, true)
//
//            groupsParticipantRepository.save(groupParticipants)
//
//        val teste : Optional<Users> = usersRepository.findById(9)
//
//            return ResponseEntity.status(HttpStatus.OK).body(teste)
//
//
//
//
//    }
//
////        var searchUsers : List<Users?>? = usersRepository.verifyExistence(user.email, user.cpf)
////        var group : Groups
////
////        return if (searchUsers?.isNotEmpty()!!){
////            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(searchUsers)
////        }
////        else{
//////            try {
////                user.password = hashpass.customPasswordEncoder()?.encode(user.password)!!
////                user.createdAt = currentDate;
////                usersRepository.save(user);
//////            }
//////            catch (err : Exception){
//////                return ResponseEntity.badRequest().build()
//////            }
//////            try {
////                group = Groups(0, "My Finances", 1, usersRepository.getUserId(user.cpf, user.email))
////
////                groupsRepository.save(group)
////
//////            }
//////            catch (err : Exception) {
//////                return ResponseEntity.badRequest().build()
//////            }
//////            try {
////                val groupParticipants = GroupParticipants(0, usersRepository.getUserId(user.cpf, user.email),
////                        groupsRepository.getGroupId(group.groupOwner), true)
////
////                groupsParticipantRepository.save(groupParticipants)
//////               return ResponseEntity.ok().build()
//////            }
//////            catch (err : java.lang.Exception) {
//////                groupsRepository.removeGroup(usersRepository.getUserId(user.cpf, user.email))
//////                usersRepository.removeUser(user.cpf, user.email)
//////            }
////            val loginUser : Users? = usersRepository.loginVerify(user.email)
////            ResponseEntity.status(HttpStatus.OK).body(searchUsers)
////        }

}