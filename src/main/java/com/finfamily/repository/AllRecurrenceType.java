package com.finfamily.repository;

import com.finfamily.domain.RecurrenceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllRecurrenceType extends JpaRepository<RecurrenceType, Integer> {
}
