package be.stijnvanbever.countryreporting.countryinformation;

import be.stijnvanbever.countryreporting.restcountries.RestCountriesResponse;
import be.stijnvanbever.countryreporting.testdata.RestCountriesResponseDataProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountryMapperTest {
    private CountryMapper countryMapper = new CountryMapper();

    @Test
    public void shouldMapToCountry_When_SupplyingRestResponse() {
        RestCountriesResponse restResponse = RestCountriesResponseDataProvider.aRestCountriesResponse();

        Country country = countryMapper.fromRestCountriesResponse(restResponse);

        assertThat(country.getCountryCode()).isEqualTo(restResponse.getCountryCode());
        assertThat(country.getName()).isEqualTo(restResponse.getName());
        assertThat(country.getCapital()).isEqualTo(restResponse.getCapital());
        assertThat(country.getFlag()).isEqualTo(restResponse.getFlag());

        // have country
        assertThat(country.getCallingCodes())
                .allSatisfy(callingCode -> assertThat(callingCode.getCountry()).isEqualTo(country));
        assertThat(country.getCurrencies())
                .allSatisfy(currency -> assertThat(currency.getCountry()).isEqualTo(country));

        // callingcodes
        assertThat(country.getCallingCodes()).hasSize(2).extracting(CallingCode::getCallingCode)
                .containsExactlyElementsOf(restResponse.getCallingCodes());

        // currencies
        assertThat(country.getCurrencies()).hasSize(2);

        for (RestCountriesResponse.Currency restCurrency : restResponse.getCurrencies()) {
            assertThat(country.getCurrencies())
                    .anySatisfy(currency -> {
                        assertThat(currency.getCode()).isEqualTo(restCurrency.getCode());
                        assertThat(currency.getName()).isEqualTo(restCurrency.getName());
                        assertThat(currency.getSymbol()).isEqualTo(restCurrency.getSymbol());
                    });
        }
    }

}