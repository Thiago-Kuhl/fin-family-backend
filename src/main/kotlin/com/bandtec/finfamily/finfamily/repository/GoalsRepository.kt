package com.bandtec.finfamily.finfamily.repository

import com.bandtec.finfamily.finfamily.model.Goals
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface GoalsRepository : JpaRepository<Goals, Int> {

    @Query(value = "SELECT * FROM goals WHERE group_id = :groupId", nativeQuery = true)
    fun getGoalsByGroupId(groupId : Int) : List<Goals>

    @Query(value = "SELECT name FROM goals WHERE id = :goalId", nativeQuery = true)
    fun getGoalName(goalId : Int) : String
}