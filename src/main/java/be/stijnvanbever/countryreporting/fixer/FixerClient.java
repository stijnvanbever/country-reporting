package be.stijnvanbever.countryreporting.fixer;

import be.stijnvanbever.countryreporting.restcountries.RestCountriesResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class FixerClient {
    final static String API_URL = "http://data.fixer.io/api/";
    private final String accessKey;
    private final RestTemplate restTemplate;

    public FixerClient(@Value("${fixer.accesskey}") String accessKey,
                       RestTemplate restTemplate) {
        this.accessKey = accessKey;
        this.restTemplate = restTemplate;
    }

    public FixerResponse fetchDataForCurrencyCodes(List<String> symbols) {
        return restTemplate.getForObject(API_URL + "latest?access_key={key}&base=EUR&symbols={symbols}",
                FixerResponse.class, accessKey, String.join(",", symbols));
    }
}
