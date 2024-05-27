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
    private Long id;

    private String nom;

    private String prenom;

    private String adresse;

    private String situationFamilial;

    private String cin;

    private String email;

    private String nomUniversite;

    private String adresseUniversite;

    private String specialite;

    private boolean assurance;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] attestationAssurance;

    private String fileNameAssurance;

    private String fileTypeAssurance;

    private String dateDebut1;

    private String dateDebut2;

    private String dateDebut3;

    private String dateFin1;

    private String dateFin2;

    private String dateFin3;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "province_id")
    private Province province1;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "province_id")
    private Province province2;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "province_id")
    private Province province3;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] demande;

    private String fileNameDemande;

    private String fileTypeDemande;
}
