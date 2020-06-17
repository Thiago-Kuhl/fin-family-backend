package com.bandtec.finfamily.finfamily.controller

import com.bandtec.finfamily.finfamily.model.GoalsTransactions
import com.bandtec.finfamily.finfamily.repository.GoalsTransactionsRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/goals/transactions")
@Api(value = "Metas", description = "Operações relacionadas as transações de metas dos grupos")
class GoalsTransactionsController {

    @Autowired
    lateinit var gtRepository: GoalsTransactionsRepository

    @GetMapping("/groups/{group_id}")
    @ApiOperation(value = "Trás as transações das metas de um grupo")
    fun getGroupGoalsTransactions(@PathVariable("group_id") groupId: Int): ResponseEntity<List<GoalsTransactions>> {
        return try {
            val transactions = gtRepository.getAllGoalsTrans(groupId)
            if (transactions.isNotEmpty()) {
                ResponseEntity.status(HttpStatus.OK).body(transactions)
            } else {
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            }
        } catch (err: Exception) {
            println(err)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @GetMapping("{goalId}")
    @ApiOperation(value = "Trás as transações de uma meta")
    fun getGoalTransactions(@PathVariable("goalId") goalId: Int): ResponseEntity<List<GoalsTransactions>> {
        return try {
            val goalTransactions = gtRepository.getGoalsByGoalId(goalId)
            if(goalTransactions.isNotEmpty()){
                ResponseEntity.status(HttpStatus.OK).body(goalTransactions)
            } else {
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            }
        }catch (err : Exception){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @PostMapping("create")
    @ApiOperation(value = "Cria uma nova transação para uma meta")
    fun createGoalTransaction(@RequestBody transaction: GoalsTransactions): ResponseEntity<String> {
        return try {
            gtRepository.save(transaction)
            ResponseEntity.status(HttpStatus.CREATED).body("Sucesso!")
        } catch (err: Exception) {
            println(err)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @DeleteMapping("remove/{id}")
    @ApiOperation(value = "Remove uma transação de uma meta")
    fun removeTransaction(@PathVariable("id") id: Int): ResponseEntity<String> {
        return try {
            gtRepository.deleteById(id)
            ResponseEntity.status(HttpStatus.OK).body("Sucesso!")
        } catch (err: Exception) {
            println(err)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }
}