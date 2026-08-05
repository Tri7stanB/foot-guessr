package com.tbart.foot_guessr.controller;

import com.tbart.foot_guessr.dto.PlayerDto;
import com.tbart.foot_guessr.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/api/players")
@RestController
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/random")
    public PlayerDto getRandomPlayer(@RequestParam(defaultValue = "false") boolean famousOnly){
        return playerService.pickRandomPlayer(famousOnly)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Aucun joueur disponible dans ce mode."));
    }

    @GetMapping("/{id}")
    public PlayerDto getPlayerById(@PathVariable(name = "id") Long playerId){
        return playerService.getPlayerById(playerId);
    }
}
