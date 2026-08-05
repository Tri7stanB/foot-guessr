package com.tbart.foot_guessr.config;

import com.tbart.foot_guessr.entities.Club;
import com.tbart.foot_guessr.entities.Contract;
import com.tbart.foot_guessr.entities.Player;
import com.tbart.foot_guessr.repositories.ClubRepository;
import com.tbart.foot_guessr.repositories.ContractRepository;
import com.tbart.foot_guessr.repositories.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DataImporter implements CommandLineRunner {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final ContractRepository contractRepository;

    public DataImporter(PlayerRepository playerRepository,
                        ClubRepository clubRepository,
                        ContractRepository contractRepository) {
        this.playerRepository = playerRepository;
        this.clubRepository = clubRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (playerRepository.count() > 0) {
            System.out.println("Base déjà peuplée, import ignoré.");
            return;
        }

        List<Player> players = importPlayers();
        importCareers(players);
    }

    private List<Player> importPlayers() throws IOException {
        List<Player> players = readCsv("players.csv").stream()
                .map(this::toPlayer)
                .toList();
        playerRepository.saveAll(players);

        System.out.println(players.size() + " joueurs importés.");
        return players;
    }

    private void importCareers(List<Player> players) throws IOException {
        Map<String, Player> playersById = players.stream()
                .collect(Collectors.toMap(Player::getWikidataId, Function.identity()));

        // LinkedHashMap : un club n'est créé qu'une fois, quel que soit le nombre de joueurs y étant passés
        Map<String, Club> clubsById = new LinkedHashMap<>();
        List<Contract> contracts = new ArrayList<>();
        int ignored = 0;

        for (String line : readCsv("careers.csv")) {
            String[] parts = line.split(",", -1);

            Player player = playersById.get(lastSegment(parts[0]));
            if (player == null || parts[4].isBlank()) {
                // joueur écarté pendant la relecture, ou contrat sans année de début
                ignored++;
                continue;
            }

            Club club = clubsById.computeIfAbsent(lastSegment(parts[2]), id -> toClub(id, parts[3]));

            Contract contract = new Contract();
            contract.setPlayer(player);
            contract.setClub(club);
            contract.setStartYear(Integer.parseInt(parts[4]));
            contract.setEndYear(parts[5].isBlank() ? null : Integer.parseInt(parts[5]));
            contracts.add(contract);
        }

        clubRepository.saveAll(clubsById.values());
        contractRepository.saveAll(contracts);
        markRetired(players, contracts);

        System.out.println(clubsById.size() + " clubs et " + contracts.size()
                + " contrats importés (" + ignored + " lignes ignorées).");
    }

    // Un joueur est considéré en activité tant qu'il a un contrat sans année de fin
    private void markRetired(List<Player> players, List<Contract> contracts) {
        Set<String> active = contracts.stream()
                .filter(contract -> contract.getEndYear() == null)
                .map(contract -> contract.getPlayer().getWikidataId())
                .collect(Collectors.toCollection(HashSet::new));

        players.forEach(player -> player.setRetired(!active.contains(player.getWikidataId())));
        playerRepository.saveAll(players);
    }

    private List<String> readCsv(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource(fileName);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().skip(1).toList();
        }
    }

    private Player toPlayer(String line) {
        String[] parts = line.split(";", -1);

        Player player = new Player();
        player.setWikidataId(lastSegment(parts[0]));
        player.setName(parts[1]);
        player.setNotoriety(Integer.parseInt(parts[2]));
        player.setBirthDate(parts[4].isBlank() ? null : LocalDate.parse(parts[4], DATE_FORMAT));
        player.setNationality(firstValue(parts[5]));
        player.setPosition(firstValue(parts[6]));
        player.setAliases(new ArrayList<>(splitValues(parts[7])));
        player.setFamous("1".equals(parts[8]));

        return player;
    }

    private Club toClub(String wikidataId, String name) {
        Club club = new Club();
        club.setWikidataId(wikidataId);
        club.setName(name.replace("\"", "").trim());
        return club;
    }

    // "http://www.wikidata.org/entity/Q12897" -> "Q12897"
    private String lastSegment(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }

    // "attaquant/milieu de terrain" -> "attaquant"
    private String firstValue(String field) {
        List<String> values = splitValues(field);
        return values.isEmpty() ? null : values.getFirst();
    }

    // Découpe sur "/", en retirant les valeurs vides et les doublons
    private List<String> splitValues(String field) {
        return Arrays.stream(field.split("/"))
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .distinct()
                .toList();
    }
}
