package be.stijnvanbever.countryreporting.reporting.informationblock;

import be.stijnvanbever.countryreporting.countryinformation.Currency;
import be.stijnvanbever.countryreporting.reporting.Report;

public class CurrencyInformationBlock extends InformationBlockComposite  {
    private final Currency currency;

    public CurrencyInformationBlock(Currency currency) {
        super(currency.getName());
        this.currency = currency;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    @Override
    public void accept(Report report) {
        report.visit(this);
    }
}
