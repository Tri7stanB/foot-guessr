package com.tbart.foot_guessr.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name="player")
@Entity
@Getter
@Setter
@ToString
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long id;

    // Clé naturelle issue de Wikidata (ex : Q12897), stable entre deux imports
    @Column(name = "wikidata_id", unique = true)
    private String wikidataId;

    // Nom d'usage : la réponse attendue dans le jeu
    @Column(name = "name")
    private String name;

    // Autres noms acceptés comme bonne réponse. Ne doit jamais partir vers le front.
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "player_alias", joinColumns = @JoinColumn(name = "player_id"))
    @Column(name = "alias")
    private List<String> aliases = new ArrayList<>();

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "position")
    private String position;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "retired")
    private Boolean retired;

    // Nombre de versions linguistiques de la page Wikipedia : indicateur de notoriété
    @Column(name = "notoriety")
    private Integer notoriety;

    // Sélection manuelle : joueurs retenus pour le mode "connus"
    @Column(name = "famous")
    private Boolean famous;

    @OneToMany(mappedBy = "player",fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Contract> career;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
