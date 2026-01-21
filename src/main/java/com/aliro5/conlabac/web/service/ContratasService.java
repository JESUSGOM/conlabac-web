package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.MovimientoEmpleadoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContratasService {

    @Value("${api.url.base}")
    private String apiUrlBase; // http://localhost:8080/api

    private final RestTemplate restTemplate = new RestTemplate();

    private String getFullUrl() {
        return apiUrlBase + "/contratas";
    }

    // Listar gente DENTRO (Activos)
    public List<MovimientoEmpleadoDTO> listarActivos(Integer idCentro) {
        // URL corregida: http://localhost:8080/api/contratas/activos?centroId=X
        String url = getFullUrl() + "/activos?centroId=" + idCentro;
        try {
            MovimientoEmpleadoDTO[] res = restTemplate.getForObject(url, MovimientoEmpleadoDTO[].class);
            return (res != null) ? Arrays.asList(res) : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error al cargar contratas activas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Registrar Entrada
    public void registrarEntrada(String nif, String cif, Integer centroId) {
        String url = getFullUrl() + "/entrada";
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("nif", nif);
            body.put("cif", cif);
            body.put("centroId", centroId);
            restTemplate.postForObject(url, body, MovimientoEmpleadoDTO.class);
        } catch (Exception e) {
            System.err.println("Error al registrar entrada de contrata: " + e.getMessage());
        }
    }

    // Registrar Salida
    public void registrarSalida(Integer idMovimiento) {
        // URL: http://localhost:8080/api/contratas/salida/{id}
        String url = getFullUrl() + "/salida/" + idMovimiento;
        try {
            restTemplate.put(url, null);
        } catch (Exception e) {
            System.err.println("Error al registrar salida de contrata: " + e.getMessage());
        }
    }
}