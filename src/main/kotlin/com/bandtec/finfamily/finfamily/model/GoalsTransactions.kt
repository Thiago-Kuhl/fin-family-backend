package com.bandtec.finfamily.finfamily.model

import io.swagger.annotations.ApiModelProperty
import javax.persistence.*

@Entity
@Table(name = "goals_transactions")
class GoalsTransactions(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(required = false, hidden = true)
    val id : Int,

    @ApiModelProperty(required = true, hidden = false)
    var reason : String,

    @ApiModelProperty(required = true, hidden = false)
    var value : Float,

    @ApiModelProperty(required = true, hidden = false)
    @Column(name = "transaction_type_id")
    val transactionTypeId : Int,

    @ApiModelProperty(required = true, hidden = false)
    @Column(name = "goal_id")
    val goalId : Int,

    @ApiModelProperty(required = true, hidden = false)
    @Column(name = "group_id")
    val groupId : Int,

    @ApiModelProperty(required = true, hidden = false)
    @Column(name ="user_id")
    val userId : Int

)