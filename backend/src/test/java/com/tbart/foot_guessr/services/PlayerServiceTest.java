package com.tbart.foot_guessr.services;

import com.tbart.foot_guessr.dto.PlayerDto;
import com.tbart.foot_guessr.entities.Player;
import com.tbart.foot_guessr.repositories.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;   // le FAUX repository

    @InjectMocks
    private PlayerService playerService;         // le VRAI service, avec le mock injecté dedans

    @Test
    void getPlayerById_returnsPlayerFromRepository() {
        // GIVEN — on prépare le contexte et on programme le mock
        Player player = new Player();
        player.setId(1L);
        PlayerDto fakePlayer = PlayerDto.from(player);
        when(playerRepository.findPlayerById(1L)).thenReturn(player);

        // WHEN — on exécute la méthode testée
        PlayerDto result = playerService.getPlayerById(1L);

        // THEN — on vérifie le résultat
        assertThat(result).isEqualTo(fakePlayer);
    }

    @Test
    void pickRandomPlayer_returnsPlayerFromRepository() {
        //GIVEN
        Player fakePlayer = new Player();
        fakePlayer.setId(1L);
        Page<Player> page = new PageImpl<>(List.of(fakePlayer));
        when(playerRepository.count()).thenReturn(3L);
        when(playerRepository.findAll(any(Pageable.class))).thenReturn(page);

        //WHEN
        Optional<PlayerDto> result = playerService.pickRandomPlayer(false);

        //THEN
        assertThat(result).contains(PlayerDto.from(fakePlayer));
    }

    @Test
    void pickRandomPlayer_whenNoPlayers_returnsEmpty() {
        when(playerRepository.count()).thenReturn(0L);

        assertThat(playerService.pickRandomPlayer(false)).isEmpty(); //Tester sur une liste vide
    }

    @Test
    void pickRandomPlayer_inFamousMode_neverPicksOutsideFamousPlayers() {
        //GIVEN
        Player fakePlayer = new Player();
        fakePlayer.setId(1L);
        when(playerRepository.countByFamousTrue()).thenReturn(2L);
        when(playerRepository.findAllByFamousTrue(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(fakePlayer)));

        //WHEN
        Optional<PlayerDto> result = playerService.pickRandomPlayer(true);

        //THEN — le tirage non filtré ne doit jamais être sollicité dans ce mode
        assertThat(result).isPresent();
        verify(playerRepository, never()).findAll(any(Pageable.class));
    }
}
