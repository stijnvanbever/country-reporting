package be.stijnvanbever.countryreporting.countryrates;

import be.stijnvanbever.countryreporting.countryinformation.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    @Query("select cr from CurrencyRate cr where cr.currency =?1 and cr.extractionTime.extractionTime > ?2")
    List<CurrencyRate> findCurrencyRateByCurrencyAndExtractionTimeGreaterThan(Currency currency, LocalDateTime time);
}
