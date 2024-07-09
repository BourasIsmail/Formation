package ma.entraide.formation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Stagiaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stagiaire_id")
    private Long id;

    //Personnel
    private String nomAr;

    private String prenomAr;

    private String nomFr;

    private String prenomFr;

    private String sexe;

    private String adresse;

    private String dateNaissance;

    private String situationFamilial;

    @Column(unique = true)
    private String cin;

    private String email;

    private String telephone;

    //Academic
    private String nomUniversite;

    private String niveauEtude;

    private String specialite;

    //Formation
    private int dureeStage;

    private String dateDebut1;

    private String dateDebut2;

    private String dateDebut3;

    private String dateFin1;

    private String dateFin2;

    private String dateFin3;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "province1_id")
    private Province province1;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "province2_id")
    private Province province2;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "province3_id")
    private Province province3;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] demande;

    private String fileNameDemande;

    private String fileTypeDemande;
}
