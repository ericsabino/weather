package br.com.ems.weather.adapter.http;

import br.com.ems.weather.core.ConsultaClimaPorCidadeBiz;
import br.com.ems.weather.core.model.Clima;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsultaClimaPorCidadeController {

    private final ConsultaClimaPorCidadeBiz consultaClimaPorCidadeBiz;

    public ConsultaClimaPorCidadeController(ConsultaClimaPorCidadeBiz consultaClimaPorCidadeBiz) {
        this.consultaClimaPorCidadeBiz = consultaClimaPorCidadeBiz;
    }

    @GetMapping("/weather/v1/city")
    public Clima buscar(@RequestParam("name") String city) {
        return consultaClimaPorCidadeBiz.buscarClimaPorCidade(city);
    }

}
