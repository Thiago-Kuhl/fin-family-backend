package com.finfamily.controller;

import com.finfamily.security.Encrypt;
import com.finfamily.domain.TodosUsuarios;
import com.finfamily.domain.Usuario;
import com.finfamily.domain.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
public class UsuariosController {

    private TodosUsuarios todosUsuarios;
    private Encrypt hashpwd = new Encrypt();
    LocalDateTime now = LocalDateTime.now();
    Password password = new com.finfamily.domain.Password();

    @Autowired
    public UsuariosController(TodosUsuarios todosUsuarios) {
        this.todosUsuarios = todosUsuarios;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> addUser(@RequestBody Usuario usuario) {
        if(todosUsuarios.verificarExistencia(usuario.getEmail(), usuario.getCpf()) != null){
            System.out.println(usuario.getSenha());
            usuario.setSenha(hashpwd.customPasswordEncoder().encode(usuario.getSenha()));
            System.out.println(usuario.getSenha());
            todosUsuarios.save(usuario);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário criado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email e/ou cpf já cadastrados!");

    }

    @PutMapping("/update/user/")
    public ResponseEntity<String> updateRegistrationData(@RequestBody Usuario user){
        if(todosUsuarios.findById(user.getIdUsuario()) != null){

            user.setSenha(todosUsuarios.getBasePasswd(user.getIdUsuario()));
            user.setDataAtualizacao(now);
            todosUsuarios.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("Dados alterados com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário não encontrado!");
    }

    @PutMapping("/update/user/password")
    public ResponseEntity<String> updateUserPassword(@RequestBody Password password){
        Usuario user;
        if(todosUsuarios.findById(password.getIdUsuario()) != null){
            if(hashpwd.customPasswordEncoder().matches(password.getBasePassword(), todosUsuarios.getBasePasswd(password.getIdUsuario()))){
                user = todosUsuarios.getUserById(password.getIdUsuario());

                user.setSenha(hashpwd.customPasswordEncoder().encode(password.getNewPassword()));
                user.setDataAtualizacao(now);
                todosUsuarios.save(user);

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
        if(todosUsuarios.findById(idUsuario) != null){
            todosUsuarios.deleteById(idUsuario);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário removido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário informado não encontrado!");
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Usuario user){
        if(todosUsuarios.buscarUsando(user.getEmail()) != null){
            return ResponseEntity.status(HttpStatus.OK).body("Usuário encontrado!");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}