package com.finfamily.controller;

//import com.finfamily.domain.GroupTypes;

import com.finfamily.domain.GroupParticipants;
import com.finfamily.domain.Groups;
import com.finfamily.domain.Users;
import com.finfamily.repository.AllGroupParticipants;
import com.finfamily.repository.AllGroups;
import com.finfamily.security.Encrypt;
import com.finfamily.repository.AllUsers;
import com.finfamily.utils.LogGenerator;
import com.finfamily.utils.Password;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class UsersController {

    private AllUsers allUsers;
    private AllGroups allGroups;
    private AllGroupParticipants allGroupParticipants;
    private Encrypt hashpwd = new Encrypt();
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private LocalDateTime now = LocalDateTime.now();
    private LogGenerator logGenerator = new LogGenerator();


    @Autowired
    public UsersController(AllUsers allUsers, AllGroups allGroups, AllGroupParticipants allGroupParticipants) {
        this.allUsers = allUsers;
        this.allGroups = allGroups;
        this.allGroupParticipants = allGroupParticipants;
    }

    @PostMapping("/api/v1/user/")
    public ResponseEntity<String> createUser(@RequestBody Users user) throws SQLException {

        if (allUsers.verifyExistence(user.getEmail(), user.getCpf()).isEmpty()) {

            try {
                user.setPassword(hashpwd.customPasswordEncoder().encode(user.getPassword()));
                user.setCreated_at(now);
                allUsers.save(user);

                try {
                    Groups group = new Groups();
                    group.setGroupName("My Finances teste");
                    group.setGroupType(1);
                    group.setGroupOwner(allUsers.getUserId(user.getCpf(), user.getEmail()));
                    allGroups.save(group);

                    try {
                        GroupParticipants groupParticipants = new GroupParticipants();

                        groupParticipants.setUserId(allUsers.getUserId(user.getCpf(), user.getEmail()));
                        groupParticipants.setGroupId(allGroups.getGroupId(group.getGroupOwner()));
                        groupParticipants.setIsManager(true);

                        allGroupParticipants.save(groupParticipants);

                    } catch (Exception err) {
                        allGroups.removeGroup(allUsers.getUserId(user.getCpf(), user.getEmail()));
                        allUsers.removeUser(user.getCpf(), user.getEmail());
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal server error!");
                    }

                } catch (Exception err) {
                    allUsers.removeUser(user.getCpf(), user.getEmail());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal server error!");
                }

            } catch (Exception err) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal server error!");
            }

            return ResponseEntity.status(HttpStatus.CREATED).body("Success!");

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal server error!");
        }

//        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("ERROR!");
    }

//    @PostMapping("/api/v1/user/login")
//    public ResponseEntity<Users> validarLogin(@RequestBody Users users) throws IOException {
//        Users user = allUsers.buscarUsando(users.getEmail());
//        if(allUsers.buscarUsando(users.getEmail()) != null){
//            if(hashpwd.customPasswordEncoder().matches(users.getPassword(), allUsers.getPasswd(users.getEmail()))) {
//
//                logGenerator.AcessLog(users.getEmail(), dtf.format(now), true);
//
//                return ResponseEntity.status(HttpStatus.OK).body(user);
//            }
//            else {
//
//                logGenerator.AcessLog(users.getEmail(), dtf.format(now), false);
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//            }
//        }
//        else {
//
//            logGenerator.AcessLog(users.getEmail(), dtf.format(now), false);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
    @PostMapping("/api/v1/user/login")
    public ResponseEntity<List<Users>> signinUser(@RequestBody Users user) throws SQLException {
        List<Users> searchUser = allUsers.loginVerify(user.getEmail());
        if(!searchUser.isEmpty()){
            String hashedPassword = allUsers.getPassword(user.getEmail());
            if(hashpwd.customPasswordEncoder().matches(user.getPassword(),hashedPassword)){
                return ResponseEntity.status(HttpStatus.OK).body(searchUser);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PutMapping("/update/user/")
    public ResponseEntity<String> updateRegistrationData(@RequestBody Users user) {
        if (allUsers.findById(user.getId()) != null) {

            user.setPassword(allUsers.getBasePasswd(user.getId()));
            user.setUpdated_at(now);
            allUsers.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("Dados alterados com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário não encontrado!");
    }

    @PutMapping("/update/user/password")
    public ResponseEntity<String> updateUserPassword(@RequestBody Password password) {
        Users user;
        if (allUsers.findById(password.getIdUsuario()) != null) {
            if (hashpwd.customPasswordEncoder().matches(password.getBasePassword(), allUsers.getBasePasswd(password.getIdUsuario()))) {
                user = allUsers.getUserById(password.getIdUsuario());

                user.setPassword(hashpwd.customPasswordEncoder().encode(password.getNewPassword()));
                user.setUpdated_at(now);
                allUsers.save(user);

                return ResponseEntity.status(HttpStatus.OK).body("Senha alterada com sucesso!");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("A senha incorreta!");
            }
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário não encontrado!");
    }

    @DeleteMapping("/remove/user")
    public ResponseEntity<String> removeUser(@RequestBody int idUsuario) {
        if (allUsers.findById(idUsuario) != null) {
            allUsers.deleteById(idUsuario);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário removido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário informado não encontrado!");
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Users user) {
        if (allUsers.buscarUsando(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Usuário encontrado!");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}