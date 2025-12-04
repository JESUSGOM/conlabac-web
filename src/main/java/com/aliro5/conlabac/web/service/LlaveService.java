package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.LlaveDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class LlaveService {

    @Value("${api.url.base}") // Toma "http://localhost:10081/api/centros"
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // Transformamos la URL base para apuntar a /llaves
    private String getApiUrl() {
        if (baseUrl == null) return "http://localhost:10081/api/llaves";
        return baseUrl.replace("/centros", "/llaves");
    }

    public List<LlaveDTO> listarPorCentro(Integer idCentro) {
        try {
            // GET http://localhost:10081/api/llaves?centroId=1
            String url = getApiUrl() + "?centroId=" + idCentro;
            LlaveDTO[] response = restTemplate.getForObject(url, LlaveDTO[].class);
            return response != null ? Arrays.asList(response) : Arrays.asList();
        } catch (Exception e) {
            e.printStackTrace();
            return Arrays.asList();
        }
    }
}