package br.com.ems.weather.core;

import br.com.ems.weather.core.model.Clima;
import br.com.ems.weather.core.ports.ClimaPorCidadePort;
import org.springframework.stereotype.Component;

@Component
public class ConsultaClimaPorCidadeBiz {
    private final ClimaPorCidadePort climaPorCidadePort;

    public ConsultaClimaPorCidadeBiz(ClimaPorCidadePort climaPorCidadePort) {
        this.climaPorCidadePort = climaPorCidadePort;
    }

    public Clima buscarClimaPorCidade(String cidade) {
        return climaPorCidadePort.buscarClimaPorCidade(cidade);
    }
}
