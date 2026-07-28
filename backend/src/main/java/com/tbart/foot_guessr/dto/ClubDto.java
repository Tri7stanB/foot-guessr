package com.tbart.foot_guessr.dto;


import com.tbart.foot_guessr.entities.Club;

public record ClubDto(String name, String country, String league) {
    public static ClubDto from(Club club) {
        return new ClubDto(club.getName(), club.getCountry(), club.getLeague());
    }
}
