package com.finfamily.controller;

import com.finfamily.domain.Users;
import com.finfamily.security.Encrypt;
import com.finfamily.utils.LogGenerator;
import com.finfamily.repository.AllUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
public class LoginController {

    private AllUsers allUsers;
    private Encrypt hashpwd = new Encrypt();
    private LogGenerator logGenerator = new LogGenerator();
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private LocalDateTime now = LocalDateTime.now();


    @Autowired
    public LoginController(AllUsers allUsers)	{
        this.allUsers = allUsers;
    }

    @PostMapping("/login")
    public ResponseEntity<Users> validarLogin(@RequestBody Users users) throws IOException {
        Users user = allUsers.buscarUsando(users.getEmail());
        if(allUsers.buscarUsando(users.getEmail()) != null){
            if(hashpwd.customPasswordEncoder().matches(users.getPassword(), allUsers.getPasswd(users.getEmail()))) {

                logGenerator.AcessLog(users.getEmail(), dtf.format(now), true);

                return ResponseEntity.status(HttpStatus.OK).body(user);
            }
            else {

                logGenerator.AcessLog(users.getEmail(), dtf.format(now), false);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        else {

            logGenerator.AcessLog(users.getEmail(), dtf.format(now), false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}