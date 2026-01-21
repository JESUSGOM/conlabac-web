package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.MovimientoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MovimientoService {

    @Value("${api.url.base}")
    private String apiUrlBase; // http://localhost:8080/api

    private final RestTemplate restTemplate = new RestTemplate();

    public List<MovimientoDTO> obtenerTodos() {
        String url = apiUrlBase + "/movimientos";
        try {
            MovimientoDTO[] respuesta = restTemplate.getForObject(url, MovimientoDTO[].class);
            return (respuesta != null) ? Arrays.asList(respuesta) : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Sincronizado con la API: llama a /api/activos?centro=X
     */
    public List<MovimientoDTO> obtenerActivosHoy(Integer idCentro) {
        String url = apiUrlBase + "/activos?centro=" + idCentro;
        try {
            MovimientoDTO[] respuesta = restTemplate.getForObject(url, MovimientoDTO[].class);
            return (respuesta != null) ? Arrays.asList(respuesta) : new ArrayList<>();
        } catch (Exception e) {
            // Log en la consola del .bat para saber si hay error de red
            System.err.println("Error en MovimientoService: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public MovimientoDTO obtenerPorId(Integer id) {
        String url = apiUrlBase + "/movimientos/" + id;
        try {
            return restTemplate.getForObject(url, MovimientoDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void guardar(MovimientoDTO movimiento) {
        String url = apiUrlBase + "/movimientos";
        try {
            restTemplate.postForObject(url, movimiento, MovimientoDTO.class);
        } catch (Exception e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    public void registrarSalida(Integer id) {
        String url = apiUrlBase + "/movimientos/" + id + "/salida";
        try {
            restTemplate.put(url, null);
        } catch (Exception e) {
            System.err.println("Error al registrar salida: " + e.getMessage());
        }
    }
}