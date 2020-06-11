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

    @GetMapping("{groupId}/entries")
    @ApiOperation(value = "Trás as entradas de um grupo")
    fun getGroupEntries(@PathVariable("groupId") groupId: Int): ResponseEntity<List<GroupsTransactions>> {

        return try {
            val transactions = gTRepository.getGroupEntries(groupId, 1)
            if (transactions.isNotEmpty()) {
                ResponseEntity.ok().body(transactions)
            } else {
                ResponseEntity.noContent().build()
            }
        } catch (err: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("{groupId}/{userId}/entries")
    @ApiOperation(value = "Trás as entradas de um grupo")
    fun getUserEntries(@PathVariable("groupId") groupId: Int, @PathVariable("userId") userId : Int): ResponseEntity<List<GroupsTransactions>> {

        return try {
            val transactions = gTRepository.getUserEntries(groupId, 1, userId)
            if (transactions.isNotEmpty()) {
                ResponseEntity.ok().body(transactions)
            } else {
                ResponseEntity.noContent().build()
            }
        } catch (err: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("{groupId}/{userId}/entries/total")
    @ApiOperation(value = "Trás o total de entradas de um usuário")
    fun getUserEntriesTotal(@PathVariable("groupId") groupId: Int, @PathVariable("userId") userId : Int): ResponseEntity<Float> {

        return try {
            val transactions = gTRepository.getUserEntries(groupId, 1, userId)
            if (transactions.isNotEmpty()) {
                var total = 0f
                transactions.forEach {
                    total += it.value
                }
                ResponseEntity.ok().body(total)
            } else {
                ResponseEntity.noContent().build()
            }
        } catch (err: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("{groupId}/expenses")
    @ApiOperation(value = "Trás as entradas de um grupo")
    fun getGroupExpenses(@PathVariable("groupId") groupId: Int): ResponseEntity<List<GroupsTransactions>> {

        return try {
            val transactions = gTRepository.getGroupEntries(groupId, 2)
            if (transactions.isNotEmpty()) {
                ResponseEntity.ok().body(transactions)
            } else {
                ResponseEntity.noContent().build()
            }
        } catch (err: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("create")
    @ApiOperation(value = "Cria uma transação para um grupo")
    fun createTransaction(@RequestBody transaction: GroupsTransactions): ResponseEntity<GroupsTransactions> {
//        if(transaction.idExpenseCategory == 0){
//            transaction.idExpenseCategory = null
//        }
        return try {
            transaction.createdAt = currentDate
            gTRepository.save(transaction)
            ResponseEntity.ok().build()
        } catch (err: Exception) {
            ResponseEntity.badRequest().build()
        }
    }
}