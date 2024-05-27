package ma.entraide.formation.repository;

import ma.entraide.formation.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepo extends JpaRepository<Region, Long> ,
        PagingAndSortingRepository<Region, Long> {

}
