package com.bandtec.finfamily.finfamily.repository

import com.bandtec.finfamily.finfamily.model.Groups
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface GroupsRepository: CrudRepository<Groups, Int> {

    @Query(value = "SELECT g.id FROM groups g WHERE g.group_type = 1 AND g.group_owner = :groupOwner", nativeQuery = true)
    fun getGroupId(groupOwner: Int): Int

    @Query(value = "SELECT COUNT(*) FROM groups WHERE group_name = :groupName AND group_type = :groupType " +
            "AND group_owner = :groupOwner", nativeQuery = true)
    fun verifyGroupToUser(groupName : String, groupType : Int, groupOwner: Int) : Int

    @Query(value = "SELECT * FROM groups WHERE group_name = :groupName AND group_type = :groupType " +
            "AND group_owner = :groupOwner", nativeQuery = true)
    fun getGroupData(groupName : String, groupType : Int, groupOwner: Int) : Groups

    @Query(value = "DELETE FROM groups WHERE group_owner = :id", nativeQuery = true)
    fun removeGroup(@Param("id") id: Int)
}