package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.GarajeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

@Service
public class GarajeService {

    @Value("${api.url.base}")
    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String getApiUrl() {
        // Construcción directa: http://localhost:8080/api/garaje
        return baseUrl + "/garaje";
    }

    public List<GarajeDTO> listar() {
        try {
            GarajeDTO[] res = restTemplate.getForObject(getApiUrl(), GarajeDTO[].class);
            return res != null ? Arrays.asList(res) : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error en GarajeService.listar: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public void guardar(GarajeDTO dto) {
        try {
            restTemplate.postForObject(getApiUrl(), dto, GarajeDTO.class);
        } catch (Exception e) {
            System.err.println("Error en GarajeService.guardar: " + e.getMessage());
        }
    }
}