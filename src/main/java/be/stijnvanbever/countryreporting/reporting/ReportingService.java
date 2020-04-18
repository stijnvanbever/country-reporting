package be.stijnvanbever.countryreporting.reporting;

import be.stijnvanbever.countryreporting.reporting.countryinformation.CountryInformationReport;
import be.stijnvanbever.countryreporting.reporting.informationblock.InformationBlock;
import be.stijnvanbever.countryreporting.reporting.markdown.MarkdownReport;
import be.stijnvanbever.countryreporting.reporting.markdown.MarkdownReportFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.Path;

@Service
public class ReportingService {
    private final String header;
    private final String fileName;
    private final CountryInformationReport countryInformationReport;
    private final MarkdownReportFactory markdownReportFactory;

    public ReportingService(@Value("${report.header}") String reportHeader,
                            @Value("${report.filename}") String reportFileName,
                            CountryInformationReport countryInformationReport,
                            MarkdownReportFactory markdownReportFactory) {
        this.header = reportHeader;
        this.fileName = reportFileName;
        this.countryInformationReport = countryInformationReport;
        this.markdownReportFactory = markdownReportFactory;
    }

    @Transactional
    public void makeReport() {
        InformationBlock countries = countryInformationReport.createCountryInformationBlock();

        MarkdownReport markdownReport = markdownReportFactory.getMarkdownReport(header);
        markdownReport.render(countries, Path.of("reports", fileName + ".html"));
    }
}
