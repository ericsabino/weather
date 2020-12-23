package br.com.ems.weather.adapter.datastore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO {
    private Integer id;
    private String main;
    private String description;
    private String icon;
}
