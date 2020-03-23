package com.bandtec.finfamily.finfamily.model

import javax.persistence.*

@Entity
@Table(name = "groups")
class Groups(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Int = 0,
        @Column(name = "group_name")
        val groupName : String = "",
        @Column(name = "group_type")
        val groupType : Int = 0,
        @Column(name = "group_owner")
        val groupOwner : Int = 0

)