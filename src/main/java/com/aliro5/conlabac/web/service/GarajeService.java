package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.GarajeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GarajeService {

    @Value("${api.url.base}")
    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String getApiUrl() {
        return baseUrl.replace("/centros", "/garaje");
    }

    public List<GarajeDTO> listar() {
        try {
            GarajeDTO[] res = restTemplate.getForObject(getApiUrl(), GarajeDTO[].class);
            return res != null ? Arrays.asList(res) : Arrays.asList();
        } catch (Exception e) {
            e.printStackTrace(); return Arrays.asList();
        }
    }

    public void guardar(GarajeDTO dto) {
        restTemplate.postForObject(getApiUrl(), dto, GarajeDTO.class);
    }
}