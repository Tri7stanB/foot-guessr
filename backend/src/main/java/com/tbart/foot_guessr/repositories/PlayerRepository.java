package com.tbart.foot_guessr.repositories;

import com.tbart.foot_guessr.entities.Player;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findPlayerById(Long id);

    // Mode "connus" : seuls les joueurs retenus lors de la sélection manuelle
    long countByFamousTrue();

    Page<Player> findAllByFamousTrue(Pageable pageable);
}
