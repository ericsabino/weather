package br.com.ems.weather.adapter.datastore.service;

import br.com.ems.weather.adapter.datastore.dto.ClimaResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "findOpenWeather", url = "${open-weather-service.ribbon.listOfServers}")
public interface ClimaPorCidadeFeignClient {

    @RequestMapping(path = "${open.weather.api.url}")
    public ClimaResponseDTO buscaClimaPorCidade(@RequestParam("q") String cidade,
                                                @RequestParam("appid") String appid,
                                                @RequestParam("lang") String multilingua,
                                                @RequestParam("units") String unidadeMedida);
}
