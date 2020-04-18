package be.stijnvanbever.countryreporting.reporting.informationblock;

import be.stijnvanbever.countryreporting.countryinformation.Country;
import be.stijnvanbever.countryreporting.reporting.Report;

public class CountryInformationBlock extends InformationBlockComposite {
    private final Country country;

    public CountryInformationBlock(Country country) {
        super(country.getName());
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }

    @Override
    public void accept(Report report) {
        report.visit(this);
    }
}
