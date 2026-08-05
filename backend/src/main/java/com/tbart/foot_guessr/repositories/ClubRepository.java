package com.tbart.foot_guessr.repositories;

import com.tbart.foot_guessr.entities.Club;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
}
