package be.stijnvanbever.countryreporting.countryrates;

import be.stijnvanbever.countryreporting.countryinformation.Currency;
import be.stijnvanbever.countryreporting.time.ExtractionTime;
import be.stijnvanbever.countryreporting.time.TimedData;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CurrencyRate extends TimedData {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Currency currency;
    private Double rate;

    public CurrencyRate() {
        super();
    }

    public CurrencyRate(Currency currency, Double rate, ExtractionTime extractionTime) {
        super(extractionTime);
        this.currency = currency;
        this.rate = rate;
    }

    public Double getRate() {
        return rate;
    }
}
