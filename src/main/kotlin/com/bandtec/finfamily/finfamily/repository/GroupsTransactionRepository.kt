package com.bandtec.finfamily.finfamily.repository

import com.bandtec.finfamily.finfamily.model.GroupsTransactions
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface GroupsTransactionRepository : JpaRepository<GroupsTransactions, Int> {

    @Query(value = "SELECT * FROM groups_transaction WHERE group_id = :groupId", nativeQuery = true)
    fun getGroupTransactions(groupId: Int): List<GroupsTransactions>

    @Query(value = "SELECT * FROM groups_transaction WHERE group_id = :groupId AND " +
            "transaction_type_id = 1 AND pay_date like :month", nativeQuery = true)
    fun getGroupEntries(groupId: Int, month: String): List<GroupsTransactions>

    @Query(value = "SELECT * FROM groups_transaction WHERE group_id = :groupId AND " +
            "transaction_type_id in (2,3) AND pay_date like :month", nativeQuery = true)
    fun getGroupExpenses(groupId: Int, month : String): List<GroupsTransactions>

    @Query(value = "SELECT * FROM groups_transaction WHERE group_id = :groupId AND " +
            "transaction_type_id = :type AND user_id = :userId", nativeQuery = true)
    fun getUserEntries(groupId: Int, type: Int, userId: Int): List<GroupsTransactions>

    @Query(value = "SELECT * FROM groups_transaction WHERE user_id = :userId AND group_id = :groupId", nativeQuery = true)
    fun getUserTransactions(userId: Int, groupId: Int): List<GroupsTransactions>

    @Query(value = "SELECT * FROM groups_transaction WHERE user_id = :userId", nativeQuery = true)
    fun getAllUserTransactions(userId: Int): List<GroupsTransactions>

    @Query(value = "SELECT * FROM groups_transaction WHERE id = :transId", nativeQuery = true)
    fun findTrans(transId: Int): GroupsTransactions

    @Query(value = "SELECT * FROM groups_transaction WHERE group_id = :groupId AND user_id = :userId", nativeQuery = true)
    fun getUserGroupTrans(userId: Int, groupId: Int) : List<GroupsTransactions>
}