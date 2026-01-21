package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.LlaveDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

@Service
public class LlaveService {

    @Value("${api.url.base}")
    private String baseUrl; // Recibe http://localhost:8080/api

    private final RestTemplate restTemplate = new RestTemplate();

    public List<LlaveDTO> listarPorCentro(Integer idCentro) {
        try {
            // Construcción robusta: http://localhost:8080/api + /llaves + ?centroId=...
            String url = baseUrl + "/llaves?centroId=" + idCentro;

            LlaveDTO[] response = restTemplate.getForObject(url, LlaveDTO[].class);
            return response != null ? Arrays.asList(response) : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error llamando al API de llaves: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}