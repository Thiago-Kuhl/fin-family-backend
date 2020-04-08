package com.bandtec.finfamily.finfamily.model

import io.swagger.annotations.ApiParam
import java.text.SimpleDateFormat
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "groups_transaction")
class GroupsTransactions (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Int = 0,

        var name : String = "",

        var description : String = "",

        var value : Float = 0F,

        @Column(name = "pay_date")
        var payDate : String = "",

        @Column(name = "is_recorrent")
        var isRecorrent : Boolean = false,

        @Column(name = "group_id")
        var groupId : Int = 0,

        @Column(name = "user_id")
        var userId : Int  = 0,

        @Column(name = "id_recurrence_type")
        var idRecurrenceType : Int = 0,

        @Column(name = "id_expense_category")
        var idExpenseCategory : Int = 0,

        @Column(name = "id_transaction_type")
        var idTransactionType : Int = 0,

        @ApiParam(hidden = true)
        @Column(name = "created_at")
        var createdAt : String = "",

        @ApiParam(hidden = true)
        @Column(name = "updated_at")
        var updatedAt : String = ""
)