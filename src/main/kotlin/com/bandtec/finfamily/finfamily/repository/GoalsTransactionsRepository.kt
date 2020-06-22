package com.bandtec.finfamily.finfamily.repository

import com.bandtec.finfamily.finfamily.model.GoalsTransactions
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface GoalsTransactionsRepository : JpaRepository<GoalsTransactions, Int> {
    @Query(value = "SELECT * FROM goals_transactions WHERE group_id = :groupId", nativeQuery = true)
    fun getAllGoalsTrans(groupId : Int) : List<GoalsTransactions>

    @Query(value = "SELECT * FROM goals_transactions WHERE goal_id = :goalId", nativeQuery = true)
    fun getGoalsByGoalId(goalId : Int) : List<GoalsTransactions>

    @Query(value = "SELECT * FROM goals_transactions WHERE user_id = :userId AND group_id = :groupId", nativeQuery = true)
    fun getUserGroupTransactions(userId : Int, groupId: Int) : List<GoalsTransactions>
}