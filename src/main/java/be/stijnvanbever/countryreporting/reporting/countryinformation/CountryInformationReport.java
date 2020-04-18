package be.stijnvanbever.countryreporting.reporting.countryinformation;

import be.stijnvanbever.countryreporting.countryinformation.Country;
import be.stijnvanbever.countryreporting.countryinformation.CountryRepository;
import be.stijnvanbever.countryreporting.countryinformation.Population;
import be.stijnvanbever.countryreporting.countryinformation.PopulationRepository;
import be.stijnvanbever.countryreporting.reporting.informationblock.CountryInformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.InformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.InformationBlockComposite;
import be.stijnvanbever.countryreporting.reporting.informationblock.PopulationInformationBlock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryInformationReport {

    private final CountryRepository countryRepository;
    private final List<CountryInformationEnhancer> enhancers;

    public CountryInformationReport(CountryRepository countryRepository,
                                    List<CountryInformationEnhancer> enhancers) {
        this.countryRepository = countryRepository;
        this.enhancers = enhancers;
    }

    public InformationBlock createCountryInformationBlock() {
        List<InformationBlock> countryBlocks = countryRepository.findAll()
                .stream()
                .map(this::createCountryInformationBlock)
                .collect(Collectors.toList());

        return new InformationBlockComposite("Countries", countryBlocks);
    }

    private CountryInformationBlock createCountryInformationBlock(Country country) {
        CountryInformationBlock countryInformationBlock = new CountryInformationBlock(country);

        enhancers.forEach(enhancer -> enhancer.enhanceCountryInformationBlock(countryInformationBlock));

        return countryInformationBlock;
    }
}
