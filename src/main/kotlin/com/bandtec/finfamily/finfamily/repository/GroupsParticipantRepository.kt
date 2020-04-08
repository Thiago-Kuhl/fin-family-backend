package com.bandtec.finfamily.finfamily.repository

import com.bandtec.finfamily.finfamily.model.GroupParticipants
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface GroupsParticipantRepository: CrudRepository<GroupParticipants, Int> {

    @Query(value = "select gp.* from group_participants gp join groups g on gp.group_id = g.id where g.id = :groupId" +
            " and g.group_type = 2", nativeQuery = true)
    fun getGroupMembers(groupId : Int) : List<GroupParticipants>


}