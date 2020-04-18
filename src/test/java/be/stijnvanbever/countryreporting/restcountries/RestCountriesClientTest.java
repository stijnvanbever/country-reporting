package be.stijnvanbever.countryreporting.restcountries;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static be.stijnvanbever.countryreporting.restcountries.RestCountriesClient.API_URL;
import static be.stijnvanbever.countryreporting.testdata.RestCountriesResponseDataProvider.aRestCountriesResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestCountriesClientTest {
    @InjectMocks
    private RestCountriesClient client;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void shouldFetchDataForCountryCode_When_CodeIsSupplied() {
        String countryCode = "AUS";

        RestCountriesResponse restResponse = aRestCountriesResponse();
        when(restTemplate.getForObject(any(String.class), eq(RestCountriesResponse.class), any(String.class)))
                .thenReturn(restResponse);

        RestCountriesResponse result = client.fetchDataByCode(countryCode);

        verify(restTemplate).getForObject(API_URL + "alpha/{countryCode}", RestCountriesResponse.class, countryCode);

        assertThat(result).isEqualTo(restResponse);
    }
}