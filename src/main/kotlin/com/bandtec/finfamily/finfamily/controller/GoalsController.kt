package com.bandtec.finfamily.finfamily.controller

import com.bandtec.finfamily.finfamily.model.Goals
import com.bandtec.finfamily.finfamily.repository.GoalsRepository
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
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @PostMapping("/")
    @ApiOperation("Cria uma meta para um grupo")
    fun createGoals(@RequestBody goals: Goals) : ResponseEntity<String>{
        return try {
            goalsRepository.save(goals)
            ResponseEntity.status(HttpStatus.CREATED).body("Sucesso!")
        }catch (err : Exception){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }
}