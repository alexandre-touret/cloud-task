package info.touret.batch.cloudtask;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryDTO {
    @JsonProperty("country_id")
    private String countryId;
    private String probability;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "CountryDTO{" +
                "countryId='" + countryId + '\'' +
                ", probability='" + probability + '\'' +
                '}';
    }

    public CountryDTO() {
    }

    public CountryDTO(String countryId, String probability) {
        this.countryId = countryId;
        this.probability = probability;
    }
}
