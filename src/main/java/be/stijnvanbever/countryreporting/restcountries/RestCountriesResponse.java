package be.stijnvanbever.countryreporting.restcountries;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RestCountriesResponse {
    private final String countryCode;
    private final String name;
    private final List<Integer> callingCodes;
    private final String capital;
    private final Long population;
    private final List<Currency> currencies;
    private final String flag;

    public RestCountriesResponse(
            @JsonProperty("alpha3Code") String countryCode,
            @JsonProperty("name") String name,
            @JsonProperty("callingCodes") List<Integer> callingCodes,
            @JsonProperty("capital") String capital,
            @JsonProperty("population") Long population,
            @JsonProperty("currencies") List<Currency> currencies,
            @JsonProperty("flag") String flag) {
        this.countryCode = countryCode;
        this.name = name;
        this.callingCodes = callingCodes;
        this.capital = capital;
        this.population = population;
        this.currencies = currencies;
        this.flag = flag;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getCallingCodes() {
        return callingCodes;
    }

    public String getCapital() {
        return capital;
    }

    public Long getPopulation() {
        return population;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public String getFlag() {
        return flag;
    }

    @Override
    public String toString() {
        return "RestCountriesResponse{" +
                "name='" + name + '\'' +
                ", callingCodes=" + callingCodes +
                ", capital='" + capital + '\'' +
                ", population=" + population +
                ", currencies=" + currencies +
                ", flag='" + flag + '\'' +
                '}';
    }

    public static class Currency {
        private final String code;
        private final String name;
        private final String symbol;

        public Currency(
                @JsonProperty("code") String code,
                @JsonProperty("name") String name,
                @JsonProperty("symbol") String symbol) {
            this.code = code;
            this.name = name;
            this.symbol = symbol;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getSymbol() {
            return symbol;
        }

        @Override
        public String toString() {
            return "Currency{" +
                    "code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    ", symbol='" + symbol + '\'' +
                    '}';
        }
    }
}
