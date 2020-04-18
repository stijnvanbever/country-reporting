package be.stijnvanbever.countryreporting.countryinformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PopulationRepository extends JpaRepository<Population, Long> {
    @Query("select p from Population p where p.country =?1 and p.extractionTime.extractionTime > ?2")
    List<Population> findPopulationByCountryAndExtractionTimeGreaterThan(Country country, LocalDateTime time);
}
