package be.stijnvanbever.countryreporting.countryinformation;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CallingCode {
    @Id
    @GeneratedValue
    private Long id;
    private Integer callingCode;
    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    public CallingCode() { }

    public CallingCode(Integer callingCode) {
        this.callingCode = callingCode;
    }

    public Integer getCallingCode() {
        return callingCode;
    }

    public void setCallingCode(Integer callingCode) {
        this.callingCode = callingCode;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
