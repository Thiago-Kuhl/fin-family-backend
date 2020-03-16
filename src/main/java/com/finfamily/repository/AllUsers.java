package com.finfamily.repository;

import com.finfamily.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AllUsers extends JpaRepository<Users, Integer > {

    @Query(value = "SELECT u.* FROM users u WHERE u.email = :email OR u.cpf = :cpf ", nativeQuery = true)
    List<Users> verifyExistence(String email, String cpf);

    @Query(value = "SELECT u.id FROM users u WHERE u.cpf = :cpf AND u.email = :email", nativeQuery = true)
    int getUserId(String cpf, String email);

    @Query(value = "SELECT u.* FROM users u WHERE u.email = :email", nativeQuery = true)
    List<Users> loginVerify(String email);

    @Query(value = "SELECT u.password FROM users u WHERE u.email = :email", nativeQuery =  true)
    String getPassword(String email);




    @Query(value = "delete from users where cpf = :cpf AND email = :email", nativeQuery = true)
    void removeUser(@Param("cpf") String cpf, @Param("email") String email);







    @Query(value = "select * from users u where u.email = :email", nativeQuery = true)
    Users buscarUsando(String email);



    @Query(value = "select u.password from users u where u.id = :id", nativeQuery = true)
    String getBasePasswd(int id);

    @Query(value = "select * from Users u where u.email = :email or u.cpf = :cpf", nativeQuery = true)
    List<Users> verificarExistencia(String email, String cpf);

//    @Transactional
//    @Modifying
//    @Query(value = "delete from Users u where u.cpf = :cpf", nativeQuery = true)
//    void removerUsuario(@Param("cpf") String cpf);

    @Query(value = "select * from Users u where u.id = :id", nativeQuery = true)
    Users getUserById(int id);

}