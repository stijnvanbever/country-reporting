package be.stijnvanbever.countryreporting.reporting.informationblock;

import be.stijnvanbever.countryreporting.reporting.Report;

public interface InformationBlock {
    void accept(Report report);
    String getHeader();
}
