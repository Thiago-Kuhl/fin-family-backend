package com.finfamily.repository;

import com.finfamily.domain.ReceipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllReceipeCategories extends JpaRepository<ReceipeCategory, Integer> {
}
