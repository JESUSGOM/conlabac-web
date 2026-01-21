package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.PaqueteDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PaqueteService {

    @Value("${api.url.base}")
    private String apiUrlBase; // http://localhost:8080/api

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Construye la URL base: http://localhost:8080/api/paqueteria
     */
    private String getFullUrl() {
        return apiUrlBase + "/paqueteria";
    }

    public List<PaqueteDTO> listarPendientes(Integer idCentro) {
        String url = getFullUrl() + "/pendientes?centroId=" + idCentro;
        try {
            PaqueteDTO[] res = restTemplate.getForObject(url, PaqueteDTO[].class);
            return (res != null) ? Arrays.asList(res) : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("PAQUETERIA: Error cargando pendientes desde " + url + " -> " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<PaqueteDTO> listarHistorial(Integer idCentro) {
        String url = getFullUrl() + "/historial?centroId=" + idCentro;
        try {
            PaqueteDTO[] res = restTemplate.getForObject(url, PaqueteDTO[].class);
            return (res != null) ? Arrays.asList(res) : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("PAQUETERIA: Error cargando historial desde " + url + " -> " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void recibir(PaqueteDTO dto) {
        try {
            restTemplate.postForObject(getFullUrl(), dto, PaqueteDTO.class);
        } catch (Exception e) {
            System.err.println("PAQUETERIA: Error al recibir paquete -> " + e.getMessage());
        }
    }

    public void entregar(Integer id) {
        String url = getFullUrl() + "/" + id + "/entregar";
        try {
            restTemplate.put(url, null);
        } catch (Exception e) {
            System.err.println("PAQUETERIA: Error al entregar paquete -> " + e.getMessage());
        }
    }
}