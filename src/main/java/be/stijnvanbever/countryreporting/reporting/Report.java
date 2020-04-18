package be.stijnvanbever.countryreporting.reporting;

import be.stijnvanbever.countryreporting.reporting.informationblock.CountryInformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.CurrencyInformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.CurrencyRateInformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.InformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.InformationBlockComposite;
import be.stijnvanbever.countryreporting.reporting.informationblock.PopulationInformationBlock;

import java.nio.file.Path;

public interface Report {
    void render(InformationBlock informationBlock, Path path);

    void visit(InformationBlockComposite informationBlockComposite);
    void visit(CountryInformationBlock countryInformationBlock);
    void visit(PopulationInformationBlock populationInformationBlock);
    void visit(CurrencyInformationBlock currencyInformationBlock);
    void visit(CurrencyRateInformationBlock currencyRateInformationBlock);
}
