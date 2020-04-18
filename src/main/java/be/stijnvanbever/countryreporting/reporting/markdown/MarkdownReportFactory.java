package be.stijnvanbever.countryreporting.reporting.markdown;

import org.springframework.stereotype.Component;

@Component
public class MarkdownReportFactory {
    public MarkdownReport getMarkdownReport(String header) {
        return new MarkdownReport(header);
    }
}
