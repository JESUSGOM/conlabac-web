package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.MovimientoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class MovimientoService {

    @Value("${api.url.base}") // Esto apunta a .../api/centros. Haremos un apaño.
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    private String getApiUrl() {
        // Truco: Reemplazamos "centros" por "movimientos" para aprovechar la config existente
        return baseUrl.replace("/centros", "/movimientos");
    }

    public List<MovimientoDTO> obtenerTodos() {
        MovimientoDTO[] respuesta = restTemplate.getForObject(getApiUrl(), MovimientoDTO[].class);
        return (respuesta != null) ? Arrays.asList(respuesta) : Arrays.asList();
    }

    public MovimientoDTO obtenerPorId(Integer id) {
        return restTemplate.getForObject(getApiUrl() + "/" + id, MovimientoDTO.class);
    }

    public void guardar(MovimientoDTO movimiento) {
        restTemplate.postForObject(getApiUrl(), movimiento, MovimientoDTO.class);
    }

    public List<MovimientoDTO> obtenerActivosHoy(Integer idCentro) {
        // Llamamos a: http://localhost:10081/api/movimientos/activos?centro={id}
        String url = getApiUrl() + "/activos?centro=" + idCentro;

        MovimientoDTO[] respuesta = restTemplate.getForObject(url, MovimientoDTO[].class);
        return (respuesta != null) ? Arrays.asList(respuesta) : Arrays.asList();
    }

    public void registrarSalida(Integer id) {
        // Llamada PUT a http://localhost:10081/api/movimientos/{id}/salida
        String url = getApiUrl() + "/" + id + "/salida";
        restTemplate.put(url, null);
    }
}