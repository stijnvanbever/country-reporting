package be.stijnvanbever.countryreporting.reporting;

import be.stijnvanbever.countryreporting.countryinformation.Country;
import be.stijnvanbever.countryreporting.countryinformation.CountryRepository;
import be.stijnvanbever.countryreporting.countryinformation.Population;
import be.stijnvanbever.countryreporting.countryinformation.PopulationRepository;
import be.stijnvanbever.countryreporting.reporting.countryinformation.CountryInformationEnhancer;
import be.stijnvanbever.countryreporting.reporting.countryinformation.CountryInformationReport;
import be.stijnvanbever.countryreporting.time.ExtractionTime;
import be.stijnvanbever.countryreporting.reporting.informationblock.CountryInformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.InformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.InformationBlockComposite;
import be.stijnvanbever.countryreporting.reporting.informationblock.PopulationInformationBlock;
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
import java.util.stream.Collectors;

import static be.stijnvanbever.countryreporting.testdata.CountryDataProvider.aCountryWithCode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryInformationReportTest {
    private CountryInformationReport countryInformationReport;

    @Mock
    private CountryRepository countryRepository;

    private List<CountryInformationEnhancer> countryInformationEnhancers;

    @BeforeEach
    public void setup() {
        Clock clock = Clock.fixed(Instant.parse("2020-03-24T10:10:05Z"), ZoneOffset.systemDefault());
        countryInformationEnhancers = List.of(
                mock(CountryInformationEnhancer.class),
                mock(CountryInformationEnhancer.class));
        countryInformationReport = new CountryInformationReport(countryRepository, countryInformationEnhancers);
    }

    @Test
    public void shouldCreateCountryInformation() {
        List<Country> countries = List.of(
                aCountryWithCode("BEL"),
                aCountryWithCode("AUS"),
                aCountryWithCode("USA")
        );

        when(countryRepository.findAll()).thenReturn(countries);

        // test
        InformationBlock informationBlock = countryInformationReport.createCountryInformationBlock();

        // verify
        assertThat(informationBlock).isInstanceOf(InformationBlockComposite.class)
                .extracting(InformationBlock::getHeader).isEqualTo("Countries");

        // verify composite
        InformationBlockComposite informationBlockComposite = (InformationBlockComposite) informationBlock;
        assertThat(informationBlockComposite.getInformationBlocks())
                .hasSize(3)
                .allSatisfy(block -> assertThat(block).isInstanceOf(CountryInformationBlock.class));

        // verify countries
        List<CountryInformationBlock> countryBlocks = informationBlockComposite.getInformationBlocks().stream()
                .map(block -> (CountryInformationBlock) block)
                .collect(Collectors.toList());

        assertThat(countryBlocks)
                .extracting(CountryInformationBlock::getCountry)
                .containsExactlyElementsOf(countries);

        countryInformationEnhancers.forEach(enhancer -> verify(enhancer, times(3)).enhanceCountryInformationBlock(any()));
    }
}