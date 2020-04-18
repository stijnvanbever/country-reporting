package be.stijnvanbever.countryreporting.reporting.informationblock;

import be.stijnvanbever.countryreporting.countryinformation.Population;
import be.stijnvanbever.countryreporting.reporting.Report;

import java.util.List;

public class PopulationInformationBlock implements InformationBlock {
    private final List<Population> populationData;

    public PopulationInformationBlock(List<Population> populationData) {
        this.populationData = populationData;
    }

    public List<Population> getPopulationData() {
        return populationData;
    }

    @Override
    public void accept(Report report) {
        report.visit(this);
    }

    @Override
    public String getHeader() {
        return "Population data";
    }
}
