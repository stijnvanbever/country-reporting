package be.stijnvanbever.countryreporting.extraction;

import be.stijnvanbever.countryreporting.countryinformation.Country;
import be.stijnvanbever.countryreporting.countryinformation.CountryService;
import be.stijnvanbever.countryreporting.countryrates.CountryRatesService;
import be.stijnvanbever.countryreporting.time.ExtractionTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ExtractionService {
    private final List<String> countryCodes;
    private final CountryService countryService;
    private final CountryRatesService countryRatesService;

    public ExtractionService(@Value("${extraction.country.codes}") List<String> countryCodes,
                             CountryService countryService,
                             CountryRatesService countryRatesService) {
        this.countryCodes = countryCodes;
        this.countryService = countryService;
        this.countryRatesService = countryRatesService;
    }

    @Transactional
    public void extractData() {
        ExtractionTime extractionTime = ExtractionTime.now();
        List<Country> countries = countryService.extractAndPersistCountryInformation(countryCodes, extractionTime);
        countryRatesService.fetchRatesForCountries(countries, extractionTime);
    }
}
