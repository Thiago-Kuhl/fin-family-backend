package com.bandtec.finfamily.finfamily.repository

import com.bandtec.finfamily.finfamily.model.GroupParticipants
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import javax.persistence.Id

interface GroupsParticipantRepository: CrudRepository<GroupParticipants, Int> {

    @Query(value = "select gp.* from group_participants gp join groups g on gp.group_id = g.id where g.id = :groupId" +
            " and g.group_type = 2", nativeQuery = true)
    fun getGroupAllMembers(groupId : Int) : List<GroupParticipants>

    @Query(value = "select * from group_participants where user_id = :userId and group_id = :groupId",
            nativeQuery = true)
    fun getGroupMember(userId: Int, groupId : Int) : List<GroupParticipants>

//    @Query(value = "SELECT groupPa from group", nativeQuery = true)
//    fun getGroupId(extId : Int) : Int

    @Query(value = "select user_id from group_participants where group_id = :groupId", nativeQuery = true)
    fun getGroupMembers(groupId: Int) : List<Int>

    @Query(value = "select COUNT(user_id) from group_participants where user_id = :userId AND group_id = :groupId",
            nativeQuery = true)
    fun verifyParticipantExistence(userId: Int, groupId: Int) : Int

    @Query(value = "select is_manager from group_participants where user_id = :userId AND group_id = :groupId",
            nativeQuery = true)
    fun isManager(userId: Int, groupId: Int) : Boolean


    @Query(value = "select COUNT(*) from group_participants where group_id = :groupId AND is_manager = 1",
            nativeQuery = true)
    fun getAllManagers(groupId: Int) : Int

    @Query(value = "select top 1 gp.* from group_participants gp join groups g on gp.group_id = g.id where g.id = :groupId" +
            " and g.group_type = 2 and gp.user_id != :userId order by id asc",
            nativeQuery = true)
    fun getNewManager(userId: Int, groupId: Int) : GroupParticipants

    @Query(value = "select DISTINCT group_id from group_participants where user_id = :userId" , nativeQuery = true)
    fun getUserGroupIds(userId: Int) : List<Int>

}