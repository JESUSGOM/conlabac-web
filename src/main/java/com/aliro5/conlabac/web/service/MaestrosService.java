package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.AlquilerDTO;
import com.aliro5.conlabac.web.dto.PlantaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

@Service
public class MaestrosService {

    @Value("${api.url.base}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<AlquilerDTO> obtenerDestinos(Integer idCentro) {
        try {
            // URL corregida para coincidir con AlquilerRestController
            String url = baseUrl + "/destinos?centroId=" + idCentro;
            AlquilerDTO[] res = restTemplate.getForObject(url, AlquilerDTO[].class);
            return res != null ? Arrays.asList(res) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<PlantaDTO> obtenerPlantas(Integer idCentro) {
        try {
            // URL corregida para coincidir con PlantaRestController
            String url = baseUrl + "/plantas?centroId=" + idCentro;
            PlantaDTO[] res = restTemplate.getForObject(url, PlantaDTO[].class);
            return res != null ? Arrays.asList(res) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}