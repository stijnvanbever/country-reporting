package be.stijnvanbever.countryreporting.reporting.informationblock;

import be.stijnvanbever.countryreporting.countryrates.CurrencyRate;
import be.stijnvanbever.countryreporting.reporting.Report;

import java.util.List;

public class CurrencyRateInformationBlock implements InformationBlock {
    private final List<CurrencyRate> currencyRates;

    public CurrencyRateInformationBlock(List<CurrencyRate> currencyRates) {
        this.currencyRates = currencyRates;
    }

    public List<CurrencyRate> getCurrencyRates() {
        return currencyRates;
    }

    @Override
    public String getHeader() {
        return "Currency rates";
    }

    @Override
    public void accept(Report report) {
        report.visit(this);
    }
}
