package com.keycorp.domain;

import com.keycorp.controller.CredenciaisTests;

import javax.persistence.*;

@Entity
@Table(name = "Usuario")
public class UsuarioTests {
    private String nome;
    private Integer idade;

    @Id
    @GeneratedValue
    private Long IdUsuario;

    @Embedded
    private CredenciaisTests credenciaisTests;

    public UsuarioTests(){}

    public UsuarioTests(String nome, Integer idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public Integer getIdade() {
        return idade;
    }

    public String getNome() {
        return nome;
    }

    public Long getIdUsuario() { return IdUsuario; }

}