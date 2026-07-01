package com.tbart.foot_guessr.services;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
        Player fakePlayer = new Player();
        fakePlayer.setId(1L);
        when(playerRepository.findPlayerById(1L)).thenReturn(fakePlayer);

        // WHEN — on exécute la méthode testée
        Player result = playerService.getPlayerById(1L);

        // THEN — on vérifie le résultat
        assertThat(result).isEqualTo(fakePlayer);
    }

    @Test
    void getRandomPlayer_returnsPlayerFromRepository(){
        //GIVEN
        Player fakePlayer = new Player();
        fakePlayer.setId(1L);
        Page<Player> page = new PageImpl<>(List.of(fakePlayer));
        when(playerRepository.count()).thenReturn(3L);
        when(playerRepository.findAll(any(Pageable.class))).thenReturn(page);

        //WHEN
        Player result = playerService.pickRandomPlayer();

        //THEN
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(page.getContent().getFirst());
    }
}
