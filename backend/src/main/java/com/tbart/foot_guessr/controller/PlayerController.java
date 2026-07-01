package com.tbart.foot_guessr.controller;

import com.tbart.foot_guessr.entities.Player;
import com.tbart.foot_guessr.services.PlayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/players")
@RestController
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/random")
    public Player getRandomPlayer(){
        return playerService.pickRandomPlayer();
    }

    @GetMapping("/{id}")
    public Player getPlayerById(@PathVariable(name = "id") Long playerId){
        return playerService.getPlayerById(playerId);
    }
}
