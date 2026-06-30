package com.tbart.foot_guessr.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Player {
    @Id
    private Long id;
    private String firstname;
    private String lastname;
    private int age;
    private String nationality;
    private Boolean retired;
    private List<Contract> career;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
