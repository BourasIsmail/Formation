package ma.entraide.formation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StagiaireConf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StagiaireConf_id")
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "stagiaire_id")
    private Stagiaire stagiaire;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "province_id")
    private Province province;

    private int duree;

    private String centre;

    private String dateDebut;

    private String dateFin;

    private String presence;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] rapport;

    private String fileNameRapport;

    private String fileTypeRapport;
}
