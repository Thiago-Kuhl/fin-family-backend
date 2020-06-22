package com.bandtec.finfamily.finfamily.model

import io.swagger.annotations.ApiModelProperty
import javax.persistence.*

@Entity
@Table(name = "goals")
class Goals (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @ApiModelProperty(required = false, hidden = true)
        val id: Int,

        @ApiModelProperty(required = true, hidden = false)
        var name : String,

        @ApiModelProperty(required = true, hidden = false)
        var description : String,

        @ApiModelProperty(required = true, hidden = false)
        var value : Float,

        @ApiModelProperty(required = true, hidden = false)
        var deadline : String,

        @ApiModelProperty(required = true, hidden = false)
        @Column(name = "group_id")
        val groupId : Int
)