package be.stijnvanbever.countryreporting.restcountries;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestCountriesClient {
    final static String API_URL = "https://restcountries.eu/rest/v2/";
    private final RestTemplate restTemplate;

    public RestCountriesClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RestCountriesResponse fetchDataByCode(String countryCode) {
        return restTemplate.getForObject(API_URL + "alpha/{countryCode}", RestCountriesResponse.class, countryCode);
    }
}
