package com.tbart.foot_guessr.repositories;

import com.tbart.foot_guessr.entities.Contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
}
