package com.finfamily.repository;

import com.finfamily.domain.PersonalWallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllPersonalWallet extends JpaRepository<PersonalWallet, Integer> {
}
