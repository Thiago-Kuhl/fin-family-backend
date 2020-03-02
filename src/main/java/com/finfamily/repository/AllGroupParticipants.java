package com.finfamily.repository;

import com.finfamily.domain.GroupParticipants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllGroupParticipants extends JpaRepository<GroupParticipants, Integer> {
}
