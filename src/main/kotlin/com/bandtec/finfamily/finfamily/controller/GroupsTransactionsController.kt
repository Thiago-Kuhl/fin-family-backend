package com.bandtec.finfamily.finfamily.controller

import com.bandtec.finfamily.finfamily.model.GroupsTransactions
import com.bandtec.finfamily.finfamily.repository.GroupsTransactionRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import java.util.*

@RestController
@RequestMapping("/api/v1/transactions")
@Api(value = "Transações", description = "Operações relacionadas as transações dos grupos")
class GroupsTransactionsController {

    @Autowired
    lateinit var gTRepository: GroupsTransactionRepository

    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())!!

    @GetMapping("{groupId}")
    @ApiOperation(value = "Trás as transações de um grupo")
    fun getGroupTransaction(@PathVariable("groupId") groupId : Int) : ResponseEntity<List<GroupsTransactions>> {

        return try{
            val transactions = gTRepository.getGroupTransactions(groupId)
            if(transactions.isNotEmpty()){
                ResponseEntity.ok().body(transactions)
            } else {
                ResponseEntity.noContent().build()
            }
        }catch (err : Exception){
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("create")
    @ApiOperation(value = "Cria uma transação para um grupo")
    fun createTransaction(@ModelAttribute transaction: GroupsTransactions) : ResponseEntity<GroupsTransactions>{
        return try{
            transaction.createdAt = currentDate
            gTRepository.save(transaction)
            ResponseEntity.ok().build()
        }catch (err : Exception){
            ResponseEntity.badRequest().build()
        }
    }
}