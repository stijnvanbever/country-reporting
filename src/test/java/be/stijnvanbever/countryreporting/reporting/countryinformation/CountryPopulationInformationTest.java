package be.stijnvanbever.countryreporting.reporting.countryinformation;

import be.stijnvanbever.countryreporting.countryinformation.Country;
import be.stijnvanbever.countryreporting.countryinformation.Population;
import be.stijnvanbever.countryreporting.countryinformation.PopulationRepository;
import be.stijnvanbever.countryreporting.reporting.informationblock.CountryInformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.InformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.PopulationInformationBlock;
import be.stijnvanbever.countryreporting.testdata.CountryDataProvider;
import be.stijnvanbever.countryreporting.time.ExtractionTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryPopulationInformationTest {
    private CountryPopulationInformation countryPopulationInformation;

    @Mock
    private PopulationRepository populationRepository;

    @Captor
    private ArgumentCaptor<LocalDateTime> dateTimeCaptor;

    private final Integer historyInDays = 5;

    @BeforeEach
    public void setup() {
        Clock clock = Clock.fixed(Instant.parse("2020-03-24T10:10:05Z"), ZoneOffset.systemDefault());
        countryPopulationInformation = new CountryPopulationInformation(historyInDays, clock, populationRepository);
    }

    @Test
    public void shouldAddPopulationInformationBlock_When_EnhancingCountryInformation() {
        // setup
        Country country = CountryDataProvider.aCountryWithCode("BEL");
        CountryInformationBlock countryBlock = new CountryInformationBlock(country);

        LocalDateTime localDateTime = LocalDateTime.parse("2020-03-21T13:37:40");

        List<Population> populationData = List.of(
                new Population(country, 12000000L, ExtractionTime.of(localDateTime.minusDays(2))),
                new Population(country, 12000100L, ExtractionTime.of(localDateTime.minusDays(1))),
                new Population(country, 12000200L, ExtractionTime.of(localDateTime))
        );
        when(populationRepository.findPopulationByCountryAndExtractionTimeGreaterThan(eq(country), any()))
                .thenReturn(populationData);

        // test
        countryPopulationInformation.enhanceCountryInformationBlock(countryBlock);

        // verify days in the past for population
        verify(populationRepository)
                .findPopulationByCountryAndExtractionTimeGreaterThan(any(Country.class), dateTimeCaptor.capture());
        assertThat(dateTimeCaptor.getAllValues()).containsOnly(LocalDateTime.parse("2020-03-19T11:10:05"));

        // verify population block
        List<InformationBlock> informationBlocks = countryBlock.getInformationBlocks();
        assertThat(informationBlocks).hasSize(1);
        PopulationInformationBlock populationBlock = (PopulationInformationBlock) informationBlocks.get(0);
        assertThat(populationBlock.getPopulationData()).hasSize(3)
                .containsExactlyElementsOf(populationData);
    }

}