package be.stijnvanbever.countryreporting.countryinformation;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Country {
    @Id
    private String countryCode;
    private String name;
    private String capital;
    private String flag;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CallingCode> callingCodes = new ArrayList<>();
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Currency> currencies = new ArrayList<>();

    public Country() {}

    public Country(String countryCode, String name, String capital, String flag) {
        this.countryCode = countryCode;
        this.name = name;
        this.capital = capital;
        this.flag = flag;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public String getFlag() {
        return flag;
    }

    public List<CallingCode> getCallingCodes() {
        return callingCodes;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void addCallingCodes(List<CallingCode> callingCodes) {
        this.callingCodes.addAll(callingCodes);
        callingCodes.forEach(callingCode -> callingCode.setCountry(this));
    }

    public void addCurrencies(List<Currency> currencies) {
        this.currencies.addAll(currencies);
        currencies.forEach(currency -> currency.setCountry(this));
    }
}
