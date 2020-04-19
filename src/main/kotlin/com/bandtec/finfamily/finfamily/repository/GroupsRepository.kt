package com.bandtec.finfamily.finfamily.repository

import com.bandtec.finfamily.finfamily.model.Groups
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface GroupsRepository: CrudRepository<Groups, Int> {

    @Query(value = "SELECT g.id FROM groups g WHERE g.group_type = 1 AND g.group_owner = :groupOwner", nativeQuery = true)
    fun getGroupIdByOwner(groupOwner: Int): Int

    @Query(value = "SELECT id FROM groups WHERE group_external_id = :externalId", nativeQuery = true)
    fun getIdByExternal(externalId : String) : Int

    @Query(value = "SELECT COUNT(*) FROM groups WHERE group_name = :groupName AND group_type = :groupType " +
            "AND group_owner = :groupOwner", nativeQuery = true)
    fun verifyGroupToUser(groupName : String, groupType : Int, groupOwner: Int) : Int

    @Query(value = "SELECT * FROM groups WHERE group_name = :groupName AND group_type = :groupType " +
            "AND group_owner = :groupOwner", nativeQuery = true)
    fun getGroupData(groupName : String, groupType : Int, groupOwner: Int) : Groups

    @Query(value = "SELECT COUNT(group_external_id) FROM groups WHERE group_external_id = :groupExternalId", nativeQuery = true)
    fun verifyGroupExternalId(groupExternalId : String) : Int

    @Query(value = "select * from groups where id = :id" , nativeQuery = true)
    fun getUserGroupIds(id: Int) : List<Groups>



}