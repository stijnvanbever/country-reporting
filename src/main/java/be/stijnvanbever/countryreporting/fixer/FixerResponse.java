package be.stijnvanbever.countryreporting.fixer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class FixerResponse {
    private final Boolean success;
    private final String base;
    private final Map<String, Double> rates;

    public FixerResponse (
            @JsonProperty("success") Boolean success,
            @JsonProperty("base") String base,
            @JsonProperty("rates") Map<String, Double> rates) {
        this.success = success;
        this.base = base;
        this.rates = rates;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getBase() {
        return base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    @Override
    public String toString() {
        return "FixerResponse{" +
                "success=" + success +
                ", base='" + base + '\'' +
                ", rates=" + rates +
                '}';
    }
}
