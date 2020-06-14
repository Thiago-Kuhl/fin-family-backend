package com.bandtec.finfamily.finfamily.repository

import com.bandtec.finfamily.finfamily.model.GroupsTransactions
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface GroupsTransactionRepository: CrudRepository<GroupsTransactions, Int> {

    @Query(value = "SELECT * FROM groups_transaction WHERE group_id = :groupId", nativeQuery = true)
    fun getGroupTransactions(groupId : Int) : List<GroupsTransactions>

    @Query(value = "SELECT * FROM groups_transaction WHERE group_id = :groupId AND " +
            "transaction_type_id = :type", nativeQuery = true)
    fun getGroupEntries(groupId : Int, type : Int) : List<GroupsTransactions>

    @Query(value = "SELECT * FROM groups_transaction WHERE group_id = :groupId AND " +
            "transaction_type_id = :type AND user_id = :userId", nativeQuery = true)
    fun getUserEntries(groupId : Int, type : Int, userId: Int) : List<GroupsTransactions>


    @Query(value = "SELECT * FROM groups_transaction WHERE user_id = :userId AND group_id = :groupId", nativeQuery = true)
    fun getUserTransactions(userId : Int, groupId: Int) : List<GroupsTransactions>

    @Query(value = "SELECT * FROM groups_transaction WHERE user_id = :userId", nativeQuery = true)
    fun getAllUserTransactions(userId: Int) : List<GroupsTransactions>

    @Query(value = "DELETE FROM groups_transaction WHERE group_id = :groupId", nativeQuery = true)
    fun removeTransactions(groupId : Int)

    @Query(value = "SELECT COUNT (id) FROM groups_transaction WHERE group_id = :groupId", nativeQuery = true)
    fun countGroupTransactions(groupId : Int) : Int

    @Query(value = "SELECT * FROM groups_transaction WHERE id = :transId", nativeQuery = true)
    fun findTrans(transId : Int) : GroupsTransactions
}