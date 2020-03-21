package com.bandtec.finfamily.finfamily.model

import javax.persistence.*

@Entity
@Table(name = "group_types")
class GroupTypes(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Int = 0,
    val type : String = ""
)