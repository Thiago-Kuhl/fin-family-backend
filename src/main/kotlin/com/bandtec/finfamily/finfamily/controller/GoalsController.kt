package com.bandtec.finfamily.finfamily.controller

import com.bandtec.finfamily.finfamily.model.Goals
import com.bandtec.finfamily.finfamily.repository.GoalsRepository
import com.bandtec.finfamily.finfamily.repository.GoalsTransactionsRepository
import com.bandtec.finfamily.finfamily.repository.GroupsTransactionRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/goals")
@Api(value = "Metas", description = "Operações relacionadas as metas dos grupos")
class GoalsController {

    @Autowired
    lateinit var goalsRepository: GoalsRepository

    @Autowired
    lateinit var gtRepository: GoalsTransactionsRepository

    @Autowired
    lateinit var groupTransRepository: GroupsTransactionRepository

    @GetMapping("{group_id}")
    @ApiOperation(value = "Trás as metas de um grupo")
    fun getGoals(@PathVariable("group_id") groupId : Int) : ResponseEntity<List<Goals>>{
        return try {
            val goals = goalsRepository.getGoalsByGroupId(groupId)
            if(goals.isNotEmpty()){
                ResponseEntity.status(HttpStatus.OK).body(goals)
            }else {
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            }
        }catch (err : Exception){
            println(err)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @PostMapping("create")
    @ApiOperation("Cria uma meta para um grupo")
    fun createGoals(@RequestBody goals: Goals) : ResponseEntity<String>{
        return try {
            goalsRepository.save(goals)
            ResponseEntity.status(HttpStatus.CREATED).body("Sucesso!")
        }catch (err : Exception){
            println(err)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @DeleteMapping("remove/{goalId}")
    @ApiOperation("Remove uma meta para um grupo")
    fun removeGoals(@PathVariable("goalId") goalId : Int) : ResponseEntity<String>{
        return try{
            val transactions = gtRepository.getGoalsByGoalId(goalId)
            val groupGoalTrans = groupTransRepository.getTransByGoal(goalId)
            if (transactions.isNotEmpty()){
                gtRepository.deleteAll(transactions)
                if (groupGoalTrans.isNotEmpty()){
                    groupTransRepository.deleteAll(groupGoalTrans)
                }
                goalsRepository.deleteById(goalId)
                ResponseEntity.status(HttpStatus.OK).body("Sucesso!")
            } else {
                if (groupGoalTrans.isNotEmpty()){
                    groupTransRepository.deleteAll(groupGoalTrans)
                }
                goalsRepository.deleteById(goalId)
                ResponseEntity.status(HttpStatus.OK).body("Sucesso!")
            }
        }catch (err : Exception){
            println(err)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }
}