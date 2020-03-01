package com.finfamily.controller;

import com.finfamily.domain.Users;
import com.finfamily.security.Encrypt;
import com.finfamily.domain.AllUsers;
import com.finfamily.domain.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
public class UsuariosController {

    private AllUsers allUsers;
    private Encrypt hashpwd = new Encrypt();
    LocalDateTime now = LocalDateTime.now();

    @Autowired
    public UsuariosController(AllUsers allUsers) {
        this.allUsers = allUsers;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> addUser(@RequestBody Users user) {
        if(allUsers.verificarExistencia(user.getEmail(), user.getCpf()) != null){
            System.out.println(user.getPassword());
            user.setPassword(hashpwd.customPasswordEncoder().encode(user.getPassword()));
            System.out.println(user.getPassword());
            user.setCreated_at(now);
            allUsers.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("User successfully created!");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email and/or cpf already registred!");

    }

    @PutMapping("/update/user/")
    public ResponseEntity<String> updateRegistrationData(@RequestBody Users user){
        if(allUsers.findById(user.getId()) != null){

            user.setPassword(allUsers.getBasePasswd(user.getId()));
            user.setUpdated_at(now);
            allUsers.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("Dados alterados com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário não encontrado!");
    }

    @PutMapping("/update/user/password")
    public ResponseEntity<String> updateUserPassword(@RequestBody Password password){
        Users user;
        if(allUsers.findById(password.getIdUsuario()) != null){
            if(hashpwd.customPasswordEncoder().matches(password.getBasePassword(), allUsers.getBasePasswd(password.getIdUsuario()))){
                user = allUsers.getUserById(password.getIdUsuario());

                user.setPassword(hashpwd.customPasswordEncoder().encode(password.getNewPassword()));
                user.setUpdated_at(now);
                allUsers.save(user);

                return ResponseEntity.status(HttpStatus.OK).body("Senha alterada com sucesso!");
            }
            else {
                return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("A senha incorreta!");
            }
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário não encontrado!");
    }

    @DeleteMapping("/remove/user")
    public ResponseEntity<String> removeUser(@RequestBody int idUsuario) {
        if(allUsers.findById(idUsuario) != null){
            allUsers.deleteById(idUsuario);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário removido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário informado não encontrado!");
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Users user){
        if(allUsers.buscarUsando(user.getEmail()) != null){
            return ResponseEntity.status(HttpStatus.OK).body("Usuário encontrado!");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}