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
    @Column(name = "contract_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "club")
    private Club club;

    @Column(name = "start_year")
    private int startYear;

    @Column(name = "end_year")
    private Integer endYear;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
