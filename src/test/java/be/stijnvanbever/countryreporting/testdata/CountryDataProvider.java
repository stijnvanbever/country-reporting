package be.stijnvanbever.countryreporting.testdata;

import be.stijnvanbever.countryreporting.countryinformation.Country;

public class CountryDataProvider {
    private CountryDataProvider() { }

    public static Country aCountryWithCode(String countryCode) {
        Country country = new Country(countryCode, "Name", "Capital", "Flag");
        return country;
    }
}
