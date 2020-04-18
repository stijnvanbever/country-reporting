package be.stijnvanbever.countryreporting.testdata;

import be.stijnvanbever.countryreporting.countryinformation.Country;
import be.stijnvanbever.countryreporting.countryinformation.Population;
import be.stijnvanbever.countryreporting.time.ExtractionTime;

public class PopulationDataProvider {
    private PopulationDataProvider() { }

    public static Population aPopulationWithAmount(Long amount, Country country) {
        return new Population(country, amount, ExtractionTime.now());
    }
}
