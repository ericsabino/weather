package br.com.ems.weather.adapter.datastore.mapper;

import br.com.ems.weather.adapter.datastore.dto.ClimaResponseDTO;
import br.com.ems.weather.core.model.Clima;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClimaMapper {
    public Clima ofClima(ClimaResponseDTO climaResponse);
}
