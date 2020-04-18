package be.stijnvanbever.countryreporting.countryinformation;

import be.stijnvanbever.countryreporting.restcountries.RestCountriesResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountryMapper {
    public Country fromRestCountriesResponse(RestCountriesResponse restCountriesResponse) {
        Country country = new Country(
                restCountriesResponse.getCountryCode(),
                restCountriesResponse.getName(),
                restCountriesResponse.getCapital(),
                restCountriesResponse.getFlag());
        country.addCallingCodes(mapCallingCodes(restCountriesResponse));
        country.addCurrencies(mapCurrencies(restCountriesResponse.getCurrencies()));

        return country;
    }

    private List<CallingCode> mapCallingCodes(RestCountriesResponse restCountriesResponse) {
        return restCountriesResponse.getCallingCodes()
                    .stream()
                    .map(CallingCode::new)
                    .collect(Collectors.toList());
    }

    private List<Currency> mapCurrencies(List<RestCountriesResponse.Currency> currencies) {
        return currencies.stream()
                    .map(currency -> new Currency(currency.getCode(), currency.getName(), currency.getSymbol()))
                    .collect(Collectors.toList());
    }
}
