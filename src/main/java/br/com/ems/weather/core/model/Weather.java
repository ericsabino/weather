package br.com.ems.weather.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
    private Integer id;
    private String main;
    private String description;
    private String icon;
}