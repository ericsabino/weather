package br.com.ems.weather.adapter.datastore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SysDTO {
    private Integer type;
    private Integer id;
    private Double message;
    private String country;
    private Long sunrise;
    private Long sunset;
}
