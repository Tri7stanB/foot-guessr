package com.tbart.foot_guessr.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Table(name="player")
@Entity
@Getter
@Setter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "position")
    private String position;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "retired")
    private Boolean retired;

    @OneToMany(mappedBy = "player",fetch = FetchType.LAZY)
    private List<Contract> career;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
