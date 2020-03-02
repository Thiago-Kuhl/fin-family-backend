package com.finfamily.repository;

import com.finfamily.domain.GroupWallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllGroupWallet extends JpaRepository<GroupWallet, Integer> {
}
