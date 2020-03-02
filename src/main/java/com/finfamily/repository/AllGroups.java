package com.finfamily.repository;

import com.finfamily.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AllGroups extends JpaRepository<Groups, Integer> {

    @Query(value = " select * from groups", nativeQuery = true)
    Groups getAllGroups();
}
