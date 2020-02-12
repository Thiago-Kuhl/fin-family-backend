package com.keycorp.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface TodosUsuarios extends JpaRepository<Usuario, Integer > {
    //linguagem HQL ou JPQL para escrever os scripts do banco
//
    @Query("select u from Usuario u where u.email = :email")
    Usuario buscarUsando(String email);

    @Query("select senha from Usuario u where u.email = :email")
    String getPasswd(String email);

    @Query("select senha from Usuario u where u.idUsuario = :userId")
    String getBasePasswd(int userId);

    @Query("select u from Usuario u where u.email = :email or u.cpf = :cpf")
    List<Usuario> verificarExistencia(String email, String cpf);

    @Transactional
    @Modifying
    @Query("delete from Usuario u where u.cpf = :cpf")
    void removerUsuario(@Param("cpf") String cpf);

    @Query(value = "select * from Usuario u where u.idUsuario = ?1", nativeQuery = true)

    @Query("select u from Usuario u where u.idUsuario = :idUsuario")
    Usuario getUserById(int idUsuario);

}