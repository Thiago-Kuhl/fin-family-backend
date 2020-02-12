package com.keycorp.domain;

import com.keycorp.controller.CredenciaisTests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TodosUsuariosTests extends JpaRepository<UsuarioTests, Long > {
    //linguagem HQL ou JPQL para escrever os scripts do banco

    @Query("select u from Usuario u where u.credenciais = :credenciais")
    UsuarioTests buscarUsando(@Param("credenciais") CredenciaisTests credenciaisTests);
}