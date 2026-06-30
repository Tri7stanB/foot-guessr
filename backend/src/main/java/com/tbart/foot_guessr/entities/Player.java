package com.tbart.foot_guessr.entities;

import jakarta.persistence.*;

import java.util.List;

@Table(name="player")
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "age")
    private int age;

    @Column(name = "position")
    private String position;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "retired")
    private Boolean retired;

    @Column(name = "career")
    private List<Contract> career;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
