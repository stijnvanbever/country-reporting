package be.stijnvanbever.countryreporting.reporting;

import be.stijnvanbever.countryreporting.reporting.countryinformation.CountryInformationReport;
import be.stijnvanbever.countryreporting.reporting.informationblock.InformationBlock;
import be.stijnvanbever.countryreporting.reporting.markdown.MarkdownReport;
import be.stijnvanbever.countryreporting.reporting.markdown.MarkdownReportFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportingServiceTest {
    private ReportingService reportingService;

    @Mock
    private CountryInformationReport countryInformationReport;

    @Mock
    private MarkdownReportFactory markdownReportFactory;

    @Captor
    private ArgumentCaptor<Path> pathCaptor;

    private final String reportHeader = "Reporting header";
    private final String reportfileName = "countries-report";

    @BeforeEach
    public void setup() {
        this.reportingService = new ReportingService(reportHeader, reportfileName,
                countryInformationReport, markdownReportFactory);
    }

    @Test
    public void shouldMakeMarkdownReportWithCountryInformation() {
        InformationBlock informationBlock = mock(InformationBlock.class);
        MarkdownReport markdownReport = mock(MarkdownReport.class);

        when(countryInformationReport.createCountryInformationBlock()).thenReturn(informationBlock);
        when(markdownReportFactory.getMarkdownReport(reportHeader)).thenReturn(markdownReport);

        reportingService.makeReport();

        verify(markdownReport).render(eq(informationBlock), pathCaptor.capture());
        assertThat(pathCaptor.getValue().toString()).isEqualTo("reports/" + reportfileName + ".html");
    }
}