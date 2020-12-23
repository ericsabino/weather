package br.com.ems.weather.adapter.datastore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoordDTO {
    @JsonProperty("lon")
    private Double longitude;
    @JsonProperty("lat")
    private Double latitude;
}
