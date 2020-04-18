package be.stijnvanbever.countryreporting;

import be.stijnvanbever.countryreporting.extraction.ExtractionService;
import be.stijnvanbever.countryreporting.reporting.ReportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class CountryReportingApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(CountryReportingApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
	}

	@Bean
	@Profile("extraction")
	public CommandLineRunner obtainCountryData(ExtractionService extractionService) {
		return args -> {
			extractionService.extractData();
		};
	}

	@Bean
	@Profile("reporting")
	public CommandLineRunner makeReport(ReportingService reportingService) {
		return args -> {
			reportingService.makeReport();
		};
	}
}
