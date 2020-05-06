package com.bandtec.finfamily.finfamily.model

import io.swagger.annotations.ApiModelProperty
import javax.persistence.*

@Entity()
@Table(name = "users")
class Users(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @ApiModelProperty(required = false, hidden = true)
        val id: Int = 0,

        @Column(name = "full_name")
        @ApiModelProperty(required = true, hidden = false)
        var fullName: String = "",

        @ApiModelProperty(required = true, hidden = false)
        val cpf: String = "",

        @ApiModelProperty(required = true, hidden = false)
        val birthday: String = "",

        @ApiModelProperty(required = true, hidden = false)
        var email: String = "",

        @ApiModelProperty(required = true, hidden = false)
        var password: String = "",

        @ApiModelProperty(required = true, hidden = false)
        var nickname: String = "",

        @ApiModelProperty(required = true, hidden = false)
        @Column(name = "phone_area_code")
        val phoneAreaCode: String = "",

        @ApiModelProperty(required = true, hidden = false)
        @Column(name = "phone_area_number")
        val phoneAreaNumber: String = "",

        @ApiModelProperty(required = false, hidden = true)
        @Column(name = "created_at")
        var createdAt: String = "",

        @ApiModelProperty(required = false, hidden = true)
        @Column(name = "updated_at")
        var updatedAt: String = ""
)


