package ma.entraide.formation.repository;

import ma.entraide.formation.entity.Province;
import ma.entraide.formation.entity.Stagiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StagiaireRepo extends JpaRepository<Stagiaire, Long>,
        PagingAndSortingRepository<Stagiaire, Long> {

}
