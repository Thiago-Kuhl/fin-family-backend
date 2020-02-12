package com.keycorp.controller;

import com.keycorp.security.Encrypt;
import com.keycorp.domain.LogGenerator;
import com.keycorp.domain.TodosUsuarios;
import com.keycorp.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@CrossOrigin
@RestController
public class LoginController {

    private TodosUsuarios todosUsuarios;
    private Encrypt hashpwd = new Encrypt();
    private LogGenerator logGenerator = new LogGenerator();
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private LocalDateTime now = LocalDateTime.now();


    @Autowired
    public LoginController(TodosUsuarios todosUsuarios)	{
        this.todosUsuarios = todosUsuarios;
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> validarLogin(@RequestBody Usuario usuario) throws IOException {
        Usuario user = todosUsuarios.buscarUsando(usuario.getEmail());
        if(todosUsuarios.buscarUsando(usuario.getEmail()) != null){
            if(hashpwd.customPasswordEncoder().matches(usuario.getSenha(), todosUsuarios.getPasswd(usuario.getEmail()))) {

                logGenerator.AcessLog(usuario.getEmail(), dtf.format(now), true);

                return ResponseEntity.status(HttpStatus.OK).body(user);
            }
            else {

                logGenerator.AcessLog(usuario.getEmail(), dtf.format(now), false);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        else {

            logGenerator.AcessLog(usuario.getEmail(), dtf.format(now), false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}