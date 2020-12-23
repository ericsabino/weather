package br.com.ems.weather.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Coord {
    @JsonProperty("lon")
    private Double longitude;
    @JsonProperty("lat")
    private Double latitude;
}
