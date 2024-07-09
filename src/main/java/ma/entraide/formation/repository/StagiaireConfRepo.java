package ma.entraide.formation.repository;

import ma.entraide.formation.entity.Province;
import ma.entraide.formation.entity.Stagiaire;
import ma.entraide.formation.entity.StagiaireConf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StagiaireConfRepo extends JpaRepository<StagiaireConf, Long>,
        PagingAndSortingRepository<StagiaireConf, Long> {
    @Query("SELECT d FROM StagiaireConf d where d.province.name = :province")
    List<StagiaireConf> findByProvince(@Param("province") String province);
}
