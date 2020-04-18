package be.stijnvanbever.countryreporting.countryrates;

import be.stijnvanbever.countryreporting.countryinformation.Country;
import be.stijnvanbever.countryreporting.countryinformation.Currency;
import be.stijnvanbever.countryreporting.time.ExtractionTime;
import be.stijnvanbever.countryreporting.fixer.FixerClient;
import be.stijnvanbever.countryreporting.fixer.FixerResponse;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryRatesService {
    private final FixerClient fixerClient;
    private final CurrencyRateRepository currencyRateRepository;

    public CountryRatesService(FixerClient fixerClient, CurrencyRateRepository currencyRateRepository) {
        this.fixerClient = fixerClient;
        this.currencyRateRepository = currencyRateRepository;
    }

    public void fetchRatesForCountries(List<Country> countries, ExtractionTime extractionTime) {
        List<Currency> currencies = countries
                .stream()
                .map(Country::getCurrencies)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        List<String> currencyCodes = currencies
                .stream()
                .map(Currency::getCode)
                .collect(Collectors.toList());

        FixerResponse fixerResponse = fixerClient.fetchDataForCurrencyCodes(currencyCodes);

        currencies.forEach(currency ->
                saveCurrencyRate(currency, fixerResponse.getRates().get(currency.getCode()), extractionTime));
    }

    private void saveCurrencyRate(Currency currency, Double rate, ExtractionTime extractionTime) {
        CurrencyRate currencyRate = new CurrencyRate(currency, rate, extractionTime);
        currencyRateRepository.save(currencyRate);
    }
}
