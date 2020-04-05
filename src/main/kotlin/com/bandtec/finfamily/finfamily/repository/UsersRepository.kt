package com.bandtec.finfamily.finfamily.repository

import com.bandtec.finfamily.finfamily.model.Users
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface UsersRepository : CrudRepository <Users, Int>{
    
    @Query(value = "SELECT u.* FROM users u WHERE u.email = :email OR u.cpf = :cpf", nativeQuery = true)
    fun getUser(email : String?, cpf : String?): Users?

    @Query(value = "SELECT u.* FROM users u WHERE u.id = :userId", nativeQuery = true)
    fun getUserById(userId : String): Users

    @Query(value = "SELECT u.* FROM users u WHERE u.email = :email", nativeQuery = true)
    fun loginVerify(email : String?): Users?

    @Query(value = "SELECT u.id FROM users u WHERE u.email = :email", nativeQuery = true)
    fun getUserId(email : String?): Int

    @Query(value = "SELECT u.password FROM users u WHERE u.email = :email", nativeQuery = true)
    fun getPassword(email: String?): String?

    @Query(value = "delete from users where cpf = :cpf AND email = :email", nativeQuery = true)
    fun removeUser(@Param("cpf") cpf: String?, @Param("email") email: String?)
}

