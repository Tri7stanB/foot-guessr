package com.tbart.foot_guessr.entities;

import jakarta.persistence.*;

@Table(name="contract")
@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @JoinColumn(name = "start")
    private int start;

    @JoinColumn(name = "end")
    private Integer end;

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
