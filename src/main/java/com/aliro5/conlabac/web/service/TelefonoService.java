package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.TelefonoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TelefonoService {

    @Value("${api.url.base}")
    private String apiUrlBase; // http://localhost:8080/api

    private final RestTemplate restTemplate = new RestTemplate();

    private String getFullUrl() {
        return apiUrlBase + "/telefonos";
    }

    public List<TelefonoDTO> listarHistorial(Integer idCentro) {
        // URL final: http://localhost:8080/api/telefonos/historial?centroId=X
        String url = getFullUrl() + "/historial?centroId=" + idCentro;
        try {
            TelefonoDTO[] res = restTemplate.getForObject(url, TelefonoDTO[].class);
            return res != null ? Arrays.asList(res) : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error al cargar historial de telefonos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void registrar(TelefonoDTO dto) {
        try {
            restTemplate.postForObject(getFullUrl(), dto, TelefonoDTO.class);
        } catch (Exception e) {
            System.err.println("Error al registrar telefono: " + e.getMessage());
        }
    }

    public void marcarComunicado(Integer id) {
        String url = getFullUrl() + "/" + id + "/comunicar";
        try {
            restTemplate.put(url, null);
        } catch (Exception e) {
            System.err.println("Error al marcar como comunicado: " + e.getMessage());
        }
    }
}