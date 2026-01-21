package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.EntreTurnoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntreTurnoService {

    @Value("${api.url.base}")
    private String apiUrlBase; // http://localhost:8080/api

    private final RestTemplate restTemplate = new RestTemplate();

    private String getFullUrl() {
        return apiUrlBase + "/entre-turnos";
    }

    public List<EntreTurnoDTO> listarHistorial(Integer idCentro) {
        String url = getFullUrl() + "/historial?centroId=" + idCentro;
        try {
            EntreTurnoDTO[] res = restTemplate.getForObject(url, EntreTurnoDTO[].class);
            return res != null ? Arrays.asList(res) : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error al cargar historial de relevos desde " + url + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<EntreTurnoDTO> listarPendientes(Integer idCentro) {
        String url = getFullUrl() + "/pendientes?centroId=" + idCentro;
        try {
            EntreTurnoDTO[] res = restTemplate.getForObject(url, EntreTurnoDTO[].class);
            return res != null ? Arrays.asList(res) : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error al cargar relevos pendientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardar(EntreTurnoDTO dto) {
        try {
            restTemplate.postForObject(getFullUrl(), dto, EntreTurnoDTO.class);
        } catch (Exception e) {
            System.err.println("Error al guardar relevo: " + e.getMessage());
        }
    }

    public void marcarLeido(Integer id, String dniUsuario) {
        String url = getFullUrl() + "/" + id + "/leer";
        try {
            Map<String, String> body = new HashMap<>();
            body.put("lector", dniUsuario);
            restTemplate.put(url, body);
        } catch (Exception e) {
            System.err.println("Error al marcar relevo como leído: " + e.getMessage());
        }
    }
}