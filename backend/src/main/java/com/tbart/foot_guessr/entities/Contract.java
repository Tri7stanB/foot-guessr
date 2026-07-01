package com.tbart.foot_guessr.entities;

import jakarta.persistence.*;

@Table(name="contract")
@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contractId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playerId")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "club")
    private Club club;

    @Column(name = "startYear")
    private int startYear;

    @Column(name = "endYear")
    private Integer endYear;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
