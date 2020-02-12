package com.keycorp.controller;

import com.keycorp.domain.TodosUsuariosTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginControllerTests {

    private TodosUsuariosTests todosUsuariosTest;

    @Autowired
    public LoginControllerTests(TodosUsuariosTests todosUsuariosTest)	{
        this.todosUsuariosTest = todosUsuariosTest;
    }

    @PostMapping("/login")
    public ResponseEntity<String> validarLogin(@RequestBody CredenciaisTests credenciaisTests) {
        System.out.println(credenciaisTests.getLogin() + "----" + credenciaisTests.getSenha());
        if(todosUsuariosTest.buscarUsando(credenciaisTests) != null) {
            return ResponseEntity.ok("Usuário encontrado!");
        }  else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado!");
        }
    }
}