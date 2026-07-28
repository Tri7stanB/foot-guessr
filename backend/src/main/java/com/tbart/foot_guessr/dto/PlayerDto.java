package com.tbart.foot_guessr.dto;

import com.tbart.foot_guessr.entities.Player;

import java.time.LocalDate;
import java.util.List;

public record PlayerDto(
        String firstname,
        String lastname,
        LocalDate birthDate,
        String nationality,
        String position,
        Boolean retired,
        List<CareerStepDto> career
) {
    public static PlayerDto from(Player player) {
        List<CareerStepDto> career = player.getCareer() == null
                ? List.of()
                : player.getCareer().stream().map(CareerStepDto::from).toList();

        return new PlayerDto(
                player.getFirstname(),
                player.getLastname(),
                player.getBirthDate(),
                player.getNationality(),
                player.getPosition(),
                player.getRetired(),
                career
        );
    }
}