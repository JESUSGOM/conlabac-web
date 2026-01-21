package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.KeyMoveDTO;
import com.aliro5.conlabac.web.dto.LlaveDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

@Service
public class KeyMoveService {

    @Value("${api.url.base}")
    private String baseUrl; // http://localhost:8080/api

    private final RestTemplate restTemplate = new RestTemplate();

    // Ruta para movimientos (entregas/devoluciones)
    private String getMovesUrl() {
        return baseUrl + "/keymoves";
    }

    // Ruta para el inventario de llaves
    private String getLlavesUrl() {
        return baseUrl + "/llaves";
    }

    // 1. Obtener préstamos activos (sin devolver)
    public List<KeyMoveDTO> listarActivos(Integer idCentro) {
        String url = getMovesUrl() + "/activos?centroId=" + idCentro;
        try {
            KeyMoveDTO[] res = restTemplate.getForObject(url, KeyMoveDTO[].class);
            return res != null ? Arrays.asList(res) : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error en listarActivos: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // 2. Obtener llaves DISPONIBLES (CORREGIDO: apunta a /api/llaves/disponibles)
    public List<LlaveDTO> listarDisponibles(Integer idCentro) {
        String url = getLlavesUrl() + "/disponibles?centroId=" + idCentro;
        try {
            LlaveDTO[] res = restTemplate.getForObject(url, LlaveDTO[].class);
            return res != null ? Arrays.asList(res) : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error en listarDisponibles: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // 3. Registrar Entrega (Salida)
    public void entregar(KeyMoveDTO dto) {
        restTemplate.postForObject(getMovesUrl(), dto, KeyMoveDTO.class);
    }

    // 4. Registrar Devolución (Entrada)
    public void devolver(Integer id) {
        String url = getMovesUrl() + "/" + id + "/devolver";
        restTemplate.put(url, null);
    }

    public List<KeyMoveDTO> listarActivosHoy(Integer idCentro) {
        String url = getMovesUrl() + "/activos-hoy?centroId=" + idCentro;
        try {
            KeyMoveDTO[] res = restTemplate.getForObject(url, KeyMoveDTO[].class);
            return res != null ? Arrays.asList(res) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}