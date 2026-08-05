package com.tbart.foot_guessr.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name="club")
@Entity
@Getter
@Setter
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;

    // Clé naturelle issue de Wikidata : sert à dédupliquer les clubs à l'import
    @Column(name = "wikidata_id", unique = true)
    private String wikidataId;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "league")
    private String league;

    // Integer et non int : l'année de fondation est absente des données Wikidata actuelles
    @Column(name = "created_in")
    private Integer createdIn;

}
