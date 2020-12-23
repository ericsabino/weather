package br.com.ems.weather.adapter.datastore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WindDTO {
    private Double speed;
    private Integer deg;
}