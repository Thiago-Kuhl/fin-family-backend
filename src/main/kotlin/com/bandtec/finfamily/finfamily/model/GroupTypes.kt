package com.bandtec.finfamily.finfamily.model

import io.swagger.annotations.ApiParam
import javax.persistence.*

@Entity
@Table(name = "group_types")
class GroupTypes(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiParam(hidden = true)
    val id : Int = 0,
    @ApiParam(required = true)
    val type : String = ""
)