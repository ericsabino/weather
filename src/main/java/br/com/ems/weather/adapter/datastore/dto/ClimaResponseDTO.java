package br.com.ems.weather.adapter.datastore.dto;

import br.com.ems.weather.core.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClimaResponseDTO {
    private Long id;
    private String name;
    private Integer cod;
    private String base;
    private Long visibility;
    private Long dt;
    private Long timezone;
    private CoordDTO coord;
    private List<WeatherDTO> weather;
    private WeatherMainDTO main;
    private WindDTO wind;
    private CloudsDTO clouds;
    private SysDTO sys;
}
