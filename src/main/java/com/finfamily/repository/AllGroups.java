package com.finfamily.repository;

import com.finfamily.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AllGroups extends JpaRepository<Groups, Integer> {

    @Query(value = " select * from groups", nativeQuery = true)
    Groups getAllGroups();

    @Query(value = "SELECT g.id FROM groups g WHERE g.group_type = 1 AND g.group_owner = :groupOwner", nativeQuery = true)
    int getGroupId(int groupOwner);

    @Query(value = "DELETE FROM groups WHERE group_owner = :id", nativeQuery = true)
    void removeGroup(@Param("id") int id);
}
