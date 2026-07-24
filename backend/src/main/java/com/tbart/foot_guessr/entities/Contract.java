package com.tbart.foot_guessr.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name="contract")
@Entity
@Getter
@Setter
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

}
