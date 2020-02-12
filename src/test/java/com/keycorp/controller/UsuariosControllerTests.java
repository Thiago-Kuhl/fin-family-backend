package com.keycorp.controller;

import com.keycorp.domain.UsuarioTests;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuariosControllerTests {

    private List<UsuarioTests> usuarioTests;

    public UsuariosControllerTests() {
        this.usuarioTests = obterTodosUsuarios();
    }

    @GetMapping("/usuarios/nome/{nomeDoUsuario}")
    public ResponseEntity<List<UsuarioTests>> obterPorNome (@PathVariable("nomeDoUsuario") String nome) {
        ArrayList<UsuarioTests> usuariosEncontrados = new ArrayList<UsuarioTests>();
        for(UsuarioTests user : usuarioTests) {
            if(nome.equals(user.getNome())) {
                usuariosEncontrados.add(user);
            }
        }
        if(usuariosEncontrados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok(usuariosEncontrados);
        }

    }


    private List<UsuarioTests> obterTodosUsuarios(){
        List<UsuarioTests> usuarioTests = new ArrayList<UsuarioTests>();
        usuarioTests.add(new UsuarioTests("Thiago", 19));
        usuarioTests.add(new UsuarioTests("Rodrigo", 10));
        usuarioTests.add(new UsuarioTests("Natalia", 17));
        usuarioTests.add(new UsuarioTests("Tayoane", 16));
        return usuarioTests;
    }
}