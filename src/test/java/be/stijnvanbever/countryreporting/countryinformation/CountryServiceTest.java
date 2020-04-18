package be.stijnvanbever.countryreporting.countryinformation;

import be.stijnvanbever.countryreporting.time.ExtractionTime;
import be.stijnvanbever.countryreporting.restcountries.RestCountriesClient;
import be.stijnvanbever.countryreporting.testdata.RestCountriesResponseDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {
    @InjectMocks
    private CountryService countryService;

    @Mock
    private RestCountriesClient restCountriesClient;

    @Spy
    private CountryMapper countryMapper;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private PopulationRepository populationRepository;

    @Captor
    private ArgumentCaptor<Country> countryCaptor;

    @Captor
    private ArgumentCaptor<Population> populationCaptor;

    @Test
    public void shouldPersistThreeCountriesAndPopulation_When_SupplyingThreeCodes() {
        // setup
        List<String> countryCodes = List.of("AUS", "BEL", "USA");
        ExtractionTime extractionTime = ExtractionTime.now();

        for (String countryCode : countryCodes) {
            when(restCountriesClient.fetchDataByCode(countryCode))
                    .thenReturn(RestCountriesResponseDataProvider.aRestCountriesResponseWithCode(countryCode));
        }

        when(countryRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // test
        countryService.extractAndPersistCountryInformation(countryCodes, extractionTime);

        // verify
        verify(countryRepository, times(3)).save(countryCaptor.capture());
        assertThat(countryCaptor.getAllValues())
                .hasSize(3)
                .extracting(Country::getCountryCode)
                .containsExactlyElementsOf(countryCodes);

        verify(populationRepository, times(3)).save(populationCaptor.capture());
        assertThat(populationCaptor.getAllValues())
                .hasSize(3)
                .allSatisfy(population -> {
                    assertThat(population.getCountry()).isNotNull();
                    assertThat(population.getExtractionTime()).isEqualTo(extractionTime);
                    assertThat(population.getPopulation()).isEqualTo(12000000L);
                });
    }
}