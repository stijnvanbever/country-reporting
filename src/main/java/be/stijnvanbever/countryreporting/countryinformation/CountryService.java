package be.stijnvanbever.countryreporting.countryinformation;

import be.stijnvanbever.countryreporting.time.ExtractionTime;
import be.stijnvanbever.countryreporting.restcountries.RestCountriesClient;
import be.stijnvanbever.countryreporting.restcountries.RestCountriesResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {
    private final RestCountriesClient restCountriesClient;
    private final CountryMapper countryMapper;
    private final CountryRepository countryRepository;
    private final PopulationRepository populationRepository;

    public CountryService(RestCountriesClient restCountriesClient,
                          CountryMapper countryMapper,
                          CountryRepository countryRepository,
                          PopulationRepository populationRepository) {
        this.restCountriesClient = restCountriesClient;
        this.countryMapper = countryMapper;
        this.countryRepository = countryRepository;
        this.populationRepository = populationRepository;
    }

    @Transactional
    public List<Country> extractAndPersistCountryInformation(List<String> countryCodes, ExtractionTime extractionTime) {
        List<Country> extractedCountries = new ArrayList<>();

        for (String countryCode : countryCodes) {
            RestCountriesResponse response = restCountriesClient.fetchDataByCode(countryCode);
            Country country = countryMapper.fromRestCountriesResponse(response);
            country = countryRepository.save(country);
            extractedCountries.add(country);

            Population population = new Population(country, response.getPopulation(), extractionTime);
            populationRepository.save(population);
        }

        return extractedCountries;
    }
}
