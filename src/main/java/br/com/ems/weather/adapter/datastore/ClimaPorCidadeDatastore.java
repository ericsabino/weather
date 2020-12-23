package br.com.ems.weather.adapter.datastore;

import br.com.ems.weather.adapter.datastore.dto.ClimaResponseDTO;
import br.com.ems.weather.adapter.datastore.mapper.ClimaMapper;
import br.com.ems.weather.adapter.datastore.service.ClimaPorCidadeFeignClient;
import br.com.ems.weather.core.model.Clima;
import br.com.ems.weather.core.ports.ClimaPorCidadePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClimaPorCidadeDatastore implements ClimaPorCidadePort {

    @Value("${open.weather.api.appid}")
    private String appid;

    @Value("${open.weather.api.lang}")
    private String lang;

    @Value("${open.weather.api.units}")
    private String unidadeMedida;

    private final ClimaPorCidadeFeignClient climaPorCidadeFeignClient;

    private final ClimaMapper climaMapper;

    public ClimaPorCidadeDatastore(ClimaPorCidadeFeignClient climaPorCidadeFeignClient, ClimaMapper climaMapper) {
        this.climaPorCidadeFeignClient = climaPorCidadeFeignClient;
        this.climaMapper = climaMapper;
    }

    @Override
    public Clima buscarClimaPorCidade(String cidade) {
        ClimaResponseDTO climaResponseDTO = climaPorCidadeFeignClient.buscaClimaPorCidade(cidade, appid, lang, unidadeMedida);
        return climaMapper.ofClima(climaResponseDTO);
    }
}
