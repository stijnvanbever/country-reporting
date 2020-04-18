package be.stijnvanbever.countryreporting.reporting.countryinformation;

import be.stijnvanbever.countryreporting.countryinformation.Population;
import be.stijnvanbever.countryreporting.countryinformation.PopulationRepository;
import be.stijnvanbever.countryreporting.reporting.informationblock.CountryInformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.PopulationInformationBlock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CountryPopulationInformation implements CountryInformationEnhancer {
    private final Integer historyInDays;
    private final Clock clock;
    private final PopulationRepository populationRepository;

    public CountryPopulationInformation(@Value("${report.history.days}") Integer historyInDays,
                                        Clock clock,
                                        PopulationRepository populationRepository) {
        this.historyInDays = historyInDays;
        this.clock = clock;
        this.populationRepository = populationRepository;
    }

    @Override
    public void enhanceCountryInformationBlock(CountryInformationBlock countryInformationBlock) {
        List<Population> populationList = populationRepository
                .findPopulationByCountryAndExtractionTimeGreaterThan(countryInformationBlock.getCountry(),
                        LocalDateTime.now(clock).minusDays(historyInDays));

        countryInformationBlock.addInformationBlock(new PopulationInformationBlock(populationList));
    }
}
