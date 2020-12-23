package br.com.ems.weather.core.ports;

import br.com.ems.weather.core.model.Clima;

public interface ClimaPorCidadePort {
    public Clima buscarClimaPorCidade(String cidade);
}
