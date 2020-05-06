package com.bandtec.finfamily.finfamily.model

import javax.persistence.*
import io.swagger.annotations.*

@Entity
@Table(name = "groups")
class Groups(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @ApiModelProperty(required = false, hidden = true)
        val id : Int = 0,
        @ApiModelProperty(required = true, hidden = false)
        @Column(name = "group_name")
        val groupName : String = "",
        @ApiModelProperty(required = true, hidden = false, allowableValues = "1,2")
        @Column(name = "group_type")
        val groupType : Int = 0,
        @ApiModelProperty(required = true, hidden = false)
        @Column(name = "group_owner")
        val groupOwner : Int = 0,
        @Column(name = "group_external_id")
        @ApiModelProperty(required = false, hidden = true)
        var externalGroupId : String =  ""

)