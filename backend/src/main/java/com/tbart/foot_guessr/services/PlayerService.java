package com.tbart.foot_guessr.services;

import com.tbart.foot_guessr.entities.Player;
import com.tbart.foot_guessr.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    public Player pickRandomPlayer(){
        long totalPlayer = playerRepository.count();
        int randomPosition = (int) (Math.random()*totalPlayer); //random() renvoie un double entre 0 et 1
        Pageable pageable = PageRequest.of(randomPosition, 1); //On définit la taille de chaque page à 1
        Page<Player> page = playerRepository.findAll(pageable);
        return page.getContent().getFirst();
    }
}
