package com.bandtec.finfamily.finfamily.repository

import com.bandtec.finfamily.finfamily.model.GoalsTransactions
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface GoalsTransactionsRepository : JpaRepository<GoalsTransactions, Int> {
    @Query(value = "SELECT * FROM goals_transactions WHERE group_id = :groupId", nativeQuery = true)
    fun getAllGoalsTrans(groupId : Int) : List<GoalsTransactions>
}