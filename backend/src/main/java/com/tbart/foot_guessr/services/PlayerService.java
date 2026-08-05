package com.tbart.foot_guessr.services;

import com.tbart.foot_guessr.dto.PlayerDto;
import com.tbart.foot_guessr.entities.Player;
import com.tbart.foot_guessr.repositories.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Tire un joueur au hasard. En mode "connus", seuls les joueurs marqués famous sont
     * éligibles ; sinon toute la base l'est. Vide si aucun joueur ne correspond.
     */
    @Transactional(readOnly = true)
    public Optional<PlayerDto> pickRandomPlayer(boolean famousOnly){
        long totalPlayer = famousOnly ? playerRepository.countByFamousTrue() : playerRepository.count();
        if (totalPlayer == 0) {
            return Optional.empty();
        }

        int randomPosition = (int) (Math.random()*totalPlayer); //random() renvoie un double entre 0 et 1
        Pageable pageable = PageRequest.of(randomPosition, 1); //On définit la taille de chaque page à 1
        Page<Player> page = famousOnly
                ? playerRepository.findAllByFamousTrue(pageable)
                : playerRepository.findAll(pageable);

        return page.getContent().stream().findFirst().map(PlayerDto::from);
    }

    @Transactional(readOnly = true)
    public PlayerDto getPlayerById(Long playerId) {
        return PlayerDto.from(playerRepository.findPlayerById(playerId));
    }}
