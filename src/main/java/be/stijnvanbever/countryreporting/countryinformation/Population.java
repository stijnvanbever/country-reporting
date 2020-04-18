package be.stijnvanbever.countryreporting.countryinformation;

import be.stijnvanbever.countryreporting.time.ExtractionTime;
import be.stijnvanbever.countryreporting.time.TimedData;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Population extends TimedData {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Country country;
    private Long population;

    public Population() {
        super();
    }

    public Population(Country country, Long population, ExtractionTime extractionTime) {
        super(extractionTime);
        this.country = country;
        this.population = population;
    }

    public Country getCountry() {
        return country;
    }

    public Long getPopulation() {
        return population;
    }
}
