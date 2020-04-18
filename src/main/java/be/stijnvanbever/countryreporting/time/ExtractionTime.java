package be.stijnvanbever.countryreporting.time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class ExtractionTime {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime extractionTime;

    public ExtractionTime() { }

    private ExtractionTime(LocalDateTime extractionTime) {
        this.extractionTime = extractionTime;
    }

    public static ExtractionTime now() {
        return new ExtractionTime(LocalDateTime.now());
    }

    public static ExtractionTime of(LocalDateTime localDateTime) {
        return new ExtractionTime(localDateTime);
    }

    @Override
    public String toString() {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(this.extractionTime);
    }
}
