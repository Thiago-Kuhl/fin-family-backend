package com.bandtec.finfamily.finfamily.controller

import com.bandtec.finfamily.finfamily.model.GoalsTransactions
import com.bandtec.finfamily.finfamily.model.GroupsTransactions
import com.bandtec.finfamily.finfamily.repository.GoalsRepository
import com.bandtec.finfamily.finfamily.repository.GoalsTransactionsRepository
import com.bandtec.finfamily.finfamily.repository.GroupsTransactionRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import java.util.*

@RestController
@RequestMapping("/api/v1/goals/transactions")
@Api(value = "Metas", description = "Operações relacionadas as transações de metas dos grupos")
class GoalsTransactionsController {

    @Autowired
    lateinit var gtRepository: GoalsTransactionsRepository

    @Autowired
    lateinit var goalRepository: GoalsRepository

    @Autowired
    lateinit var groupTransRepo: GroupsTransactionRepository

    final val sdf = SimpleDateFormat("dd/MM/yyyy")
    val currentDate = sdf.format(Date())!!


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
            if (goalTransactions.isNotEmpty()) {
                ResponseEntity.status(HttpStatus.OK).body(goalTransactions)
            } else {
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            }
        } catch (err: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @PostMapping("create")
    @ApiOperation(value = "Cria uma nova transação para uma meta")
    fun createGoalTransaction(@RequestBody transaction: GoalsTransactions): ResponseEntity<String> {
        return try {
            gtRepository.save(transaction)
            return try {
                val goalName = goalRepository.getGoalName(transaction.goalId)
                val transValue = transaction.value
                val groupId = transaction.groupId
                val userId = transaction.userId
                val goalId = transaction.goalId
                val groupTrans = GroupsTransactions(null, "Meta $goalName", goalName, transValue,
                        currentDate, false, groupId, userId,1,33,
                        null,3,currentDate,null,goalId)
                groupTransRepo.save(groupTrans)
                ResponseEntity.status(HttpStatus.CREATED).body("Sucesso!")
            } catch (err: Exception) {
                gtRepository.delete(transaction)
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
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