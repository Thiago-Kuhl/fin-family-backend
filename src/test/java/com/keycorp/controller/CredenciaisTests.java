package com.keycorp.controller;

import javax.persistence.Embeddable;

@Embeddable
public class CredenciaisTests {
    private String login;
    private String senha;

    public CredenciaisTests(){}

    public CredenciaisTests(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }


    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public void set(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}