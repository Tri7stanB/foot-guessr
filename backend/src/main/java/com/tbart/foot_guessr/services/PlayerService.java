package com.tbart.foot_guessr.services;

import com.tbart.foot_guessr.dto.PlayerDto;
import com.tbart.foot_guessr.entities.Player;
import com.tbart.foot_guessr.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Transactional(readOnly = true)
    public PlayerDto pickRandomPlayer(){
        long totalPlayer = playerRepository.count();
        int randomPosition = (int) (Math.random()*totalPlayer); //random() renvoie un double entre 0 et 1
        Pageable pageable = PageRequest.of(randomPosition, 1); //On définit la taille de chaque page à 1
        Page<Player> page = playerRepository.findAll(pageable);
        return PlayerDto.from(page.getContent().getFirst());
    }

    @Transactional(readOnly = true)
    public PlayerDto getPlayerById(Long playerId) {
        return PlayerDto.from(playerRepository.findPlayerById(playerId));
    }}
