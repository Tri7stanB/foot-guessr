package com.tbart.foot_guessr.dto;

import com.tbart.foot_guessr.entities.Contract;

public record CareerStepDto(ClubDto club, int startYear, Integer endYear) {
    public static CareerStepDto from(Contract contract){
        return new CareerStepDto(ClubDto.from(contract.getClub()), contract.getStartYear(), contract.getEndYear());
    }
}
