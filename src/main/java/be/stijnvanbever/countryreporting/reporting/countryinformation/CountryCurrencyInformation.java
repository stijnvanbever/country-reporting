package be.stijnvanbever.countryreporting.reporting.countryinformation;

import be.stijnvanbever.countryreporting.countryinformation.Currency;
import be.stijnvanbever.countryreporting.countryrates.CurrencyRate;
import be.stijnvanbever.countryreporting.countryrates.CurrencyRateRepository;
import be.stijnvanbever.countryreporting.reporting.informationblock.CountryInformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.CurrencyInformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.CurrencyRateInformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.InformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.InformationBlockComposite;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryCurrencyInformation implements CountryInformationEnhancer {
    private final Integer historyInDays;
    private final Clock clock;
    private final CurrencyRateRepository currencyRateRepository;

    public CountryCurrencyInformation(@Value("${report.history.days}") Integer historyInDays,
                                      Clock clock,
                                      CurrencyRateRepository currencyRateRepository) {
        this.historyInDays = historyInDays;
        this.clock = clock;
        this.currencyRateRepository = currencyRateRepository;
    }

    @Override
    public void enhanceCountryInformationBlock(CountryInformationBlock countryInformationBlock) {
        List<Currency> currencies = countryInformationBlock.getCountry().getCurrencies();

        List<InformationBlock> currencyBlocks = currencies
                .stream()
                .map(this::createCurrencyInformationBlock)
                .collect(Collectors.toList());

        InformationBlockComposite informationBlockComposite = new InformationBlockComposite("Currencies", currencyBlocks);
        countryInformationBlock.addInformationBlock(informationBlockComposite);
    }

    private CurrencyInformationBlock createCurrencyInformationBlock(Currency currency) {
        CurrencyInformationBlock currencyBlock = new CurrencyInformationBlock(currency);

        List<CurrencyRate> currencyRates = currencyRateRepository
                .findCurrencyRateByCurrencyAndExtractionTimeGreaterThan(currency,
                        LocalDateTime.now(clock).minusDays(historyInDays));

        CurrencyRateInformationBlock currencyRateBlock = new CurrencyRateInformationBlock(currencyRates);
        currencyBlock.addInformationBlock(currencyRateBlock);
        return currencyBlock;
    }
}
