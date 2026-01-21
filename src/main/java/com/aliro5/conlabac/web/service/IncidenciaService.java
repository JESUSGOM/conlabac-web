package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.IncidenciaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class IncidenciaService {

    @Value("${api.url.base}")
    private String apiUrlBase; // http://localhost:8080/api

    private final RestTemplate restTemplate = new RestTemplate();

    private String getFullUrl() {
        return apiUrlBase + "/incidencias";
    }

    public List<IncidenciaDTO> listarPorCentro(Integer idCentro) {
        String url = getFullUrl() + "?centroId=" + idCentro;
        try {
            IncidenciaDTO[] res = restTemplate.getForObject(url, IncidenciaDTO[].class);
            return (res != null) ? Arrays.asList(res) : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error al cargar incidencias desde " + url + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardar(IncidenciaDTO dto) {
        try {
            restTemplate.postForObject(getFullUrl(), dto, IncidenciaDTO.class);
        } catch (Exception e) {
            System.err.println("Error al guardar incidencia: " + e.getMessage());
        }
    }

    public IncidenciaDTO obtenerPorId(Integer id) {
        try {
            return restTemplate.getForObject(getFullUrl() + "/" + id, IncidenciaDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
}