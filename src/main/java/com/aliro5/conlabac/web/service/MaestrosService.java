package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.AlquilerDTO;
import com.aliro5.conlabac.web.dto.PlantaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class MaestrosService {

    @Value("${api.url.base}") // Apunta a .../api/centros
    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String getBaseUrl() { return baseUrl.replace("/centros", "/maestros"); }

    public List<AlquilerDTO> obtenerDestinos(Integer idCentro) {
        String url = getBaseUrl() + "/destinos/" + idCentro;
        AlquilerDTO[] res = restTemplate.getForObject(url, AlquilerDTO[].class);
        return res != null ? Arrays.asList(res) : Arrays.asList();
    }

    public List<PlantaDTO> obtenerPlantas(Integer idCentro) {
        String url = getBaseUrl() + "/plantas/" + idCentro;
        PlantaDTO[] res = restTemplate.getForObject(url, PlantaDTO[].class);
        return res != null ? Arrays.asList(res) : Arrays.asList();
    }
}