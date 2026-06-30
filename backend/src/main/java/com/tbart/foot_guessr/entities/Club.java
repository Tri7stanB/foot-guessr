package com.tbart.foot_guessr.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Club {
    @Id
    private Long id;
    private String name;
    private String country;
    private String league;
    private int created_in;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}