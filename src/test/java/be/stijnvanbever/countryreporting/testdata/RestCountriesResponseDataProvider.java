package be.stijnvanbever.countryreporting.testdata;

import be.stijnvanbever.countryreporting.restcountries.RestCountriesResponse;

import java.util.List;

public class RestCountriesResponseDataProvider {
    private RestCountriesResponseDataProvider() {
    }

    public static RestCountriesResponse aRestCountriesResponse() {
        return aRestCountriesResponseWithCode("CountryCode");
    }

    public static RestCountriesResponse aRestCountriesResponseWithCode(String countryCode) {
        RestCountriesResponse.Currency currency =
                new RestCountriesResponse.Currency("code", "name", "symbol");
        RestCountriesResponse.Currency currency2 =
                new RestCountriesResponse.Currency("code2", "name2", "symbol2");
        return new RestCountriesResponse(
                countryCode, "Name", List.of(23, 24), "capital", 12000000L,
                List.of(currency, currency2), "https://flagurl");
    }
}
