package com.tbart.foot_guessr.entities;

import jakarta.persistence.*;

@Table(name="club")
@Entity
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "league")
    private String league;

    @Column(name = "created_in")
    private int created_in;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}