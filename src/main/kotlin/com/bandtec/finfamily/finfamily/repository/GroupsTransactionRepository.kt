package com.bandtec.finfamily.finfamily.repository

import com.bandtec.finfamily.finfamily.model.GroupsTransactions
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface GroupsTransactionRepository: CrudRepository<GroupsTransactions, Int> {

    @Query(value = "SELECT * FROM groups_transaction WHERE group_id = :groupId", nativeQuery = true)
    fun getGroupTransactions(groupId : Int) : List<GroupsTransactions>

    @Query(value = "SELECT * FROM groups_transaction WHERE user_id = :userId AND group_id = :groupId", nativeQuery = true)
    fun getUserTransactions(userId : Int, groupId: Int) : List<GroupsTransactions>

    @Query(value = "DELETE FROM groups_transaction WHERE group_id = :groupId", nativeQuery = true)
    fun removeTransactions(groupId : Int)

    @Query(value = "SELECT COUNT (id) FROM groups_transaction WHERE group_id = :groupId", nativeQuery = true)
    fun countGroupTransactions(groupId : Int) : Int
}