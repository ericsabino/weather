package br.com.ems.weather.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Clima {
    private Long id;
    private String name;
    private Integer cod;
    private String base;
    private Long visibility;
    private Long dt;
    private Long timezone;
    private Coord coord;
    private List<Weather> weather;
    private WeatherMain main;
    private Wind wind;
    private Clouds clouds;
    private Sys sys;

}
