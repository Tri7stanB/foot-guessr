package com.tbart.foot_guessr.repositories;

import com.tbart.foot_guessr.entities.Player;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
    Player findPlayerById(Long id);
}