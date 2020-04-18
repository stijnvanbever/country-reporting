package be.stijnvanbever.countryreporting.reporting.markdown;

import be.stijnvanbever.countryreporting.countryinformation.Country;
import be.stijnvanbever.countryreporting.countryinformation.Currency;
import be.stijnvanbever.countryreporting.reporting.Report;
import be.stijnvanbever.countryreporting.reporting.informationblock.CountryInformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.CurrencyInformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.CurrencyRateInformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.InformationBlock;
import be.stijnvanbever.countryreporting.reporting.informationblock.InformationBlockComposite;
import be.stijnvanbever.countryreporting.reporting.informationblock.PopulationInformationBlock;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MarkdownReport implements Report {
    private static final String LS = System.lineSeparator();

    private StringBuilder markdown;
    private int currentLevel = 1;

    public MarkdownReport(String header) {
        markdown = new StringBuilder();
        markdown.append("# ").append(header).append(LS);
        currentLevel++;
    }

    @Override
    public void render(InformationBlock informationBlock, Path path) {
        informationBlock.accept(this);
        renderMarkdownToHtml(path);
    }

    private void renderMarkdownToHtml(Path path) {
        MutableDataSet options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, List.of(TablesExtension.create()));

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse(this.markdown.toString());
        String html = renderer.render(document);
        try {
            Files.writeString(path, html);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void visit(InformationBlockComposite informationBlockComposite) {
        addHeader(informationBlockComposite.getHeader());

        visitChildren(informationBlockComposite.getInformationBlocks());
    }

    @Override
    public void visit(CountryInformationBlock countryInformationBlock) {
        addHeader(countryInformationBlock.getHeader());

        Country country = countryInformationBlock.getCountry();

        markdown.append("<img src=\"").append(country.getFlag()).append("\" height=\"100px\" />").append(LS).append(LS);
        markdown.append("* Countrycode: ").append(country.getCountryCode()).append(LS);
        markdown.append("* Capital: ").append(country.getCapital()).append(LS);
        markdown.append(LS);

        visitChildren(countryInformationBlock.getInformationBlocks());
    }

    @Override
    public void visit(PopulationInformationBlock populationInformationBlock) {
        addHeader(populationInformationBlock.getHeader());

        markdown.append("|").append("Time").append("|").append("Population").append("|").append(LS);
        markdown.append("|").append("---").append("|").append("---").append("|").append(LS);

        populationInformationBlock.getPopulationData()
                .forEach(population ->
                    markdown.append("|")
                            .append(population.getExtractionTime())
                            .append("|")
                            .append(population.getPopulation())
                            .append("|")
                            .append(LS)
                );
    }

    @Override
    public void visit(CurrencyInformationBlock currencyBlock) {
        addHeader(currencyBlock.getHeader());

        Currency currency = currencyBlock.getCurrency();

        markdown.append("* Code: ").append(currency.getCode()).append(LS);
        markdown.append("* Symbol: ").append(currency.getSymbol()).append(LS);
        markdown.append(LS);

        visitChildren(currencyBlock.getInformationBlocks());
    }

    private void visitChildren(List<InformationBlock> informationBlocks) {
        currentLevel++;
        informationBlocks.forEach(informationBlock -> informationBlock.accept(this));
        currentLevel--;
    }

    @Override
    public void visit(CurrencyRateInformationBlock currencyRateBlock) {
        addHeader(currencyRateBlock.getHeader());

        markdown.append("|").append("Time").append("|").append("Rate").append("|").append(LS);
        markdown.append("|").append("---").append("|").append("---").append("|").append(LS);

        currencyRateBlock.getCurrencyRates()
                .forEach(currencyRate ->
                        markdown.append("|")
                                .append(currencyRate.getExtractionTime())
                                .append("|")
                                .append(currencyRate.getRate())
                                .append("|")
                                .append(LS)
                );
    }

    public void addHeader(String header) {
        markdown.append("#".repeat(currentLevel)).append(" ")
                .append(header)
                .append(LS);
    }
}
