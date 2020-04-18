package be.stijnvanbever.countryreporting.time;

import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public abstract class TimedData {
    @OneToOne(cascade = CascadeType.ALL)
    private ExtractionTime extractionTime;

    protected TimedData() {
    }

    protected TimedData(ExtractionTime extractionTime) {
        this.extractionTime = extractionTime;
    }

    public ExtractionTime getExtractionTime() {
        return extractionTime;
    }
}
