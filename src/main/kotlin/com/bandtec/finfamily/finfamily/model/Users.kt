package com.bandtec.finfamily.finfamily.model

import javax.persistence.*

@Entity()
@Table(name = "users")
class Users (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Int = 0,
    @Column(name = "full_name")
    val fullName : String = "",
    val cpf : String = "",
    val birthday : String = "",
    val email : String = "",
    var password : String = "",
    val nickname : String = "",
    @Column(name = "phone_area_code")
    val phoneAreaCode : String = "",
    @Column(name = "phone_area_number")
    val phoneAreaNumber : String = "",
    @Column(name = "created_at")
    var createdAt : String = "",
    @Column(name = "updated_at")
    var updatedAt : String = ""

)


