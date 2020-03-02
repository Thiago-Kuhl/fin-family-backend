package com.finfamily.repository;

import com.finfamily.domain.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllExpenseCategories extends JpaRepository<ExpenseCategory, Integer> {
}
