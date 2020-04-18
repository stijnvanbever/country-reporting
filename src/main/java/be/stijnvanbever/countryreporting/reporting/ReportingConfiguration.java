package be.stijnvanbever.countryreporting.reporting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ReportingConfiguration {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
