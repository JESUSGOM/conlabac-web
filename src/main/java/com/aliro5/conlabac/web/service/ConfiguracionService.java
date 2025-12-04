package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.AlquilerDTO;
import com.aliro5.conlabac.web.dto.PlantaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ConfiguracionService {

    @Value("${api.url.base}")
    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String getApiUrl() {
        return baseUrl.replace("/centros", "/maestros");
    }

    // --- ALQUILERES (EMPRESAS) ---
    public List<AlquilerDTO> listarAlquileres(Integer idCentro) {
        String url = getApiUrl() + "/alquileres?centroId=" + idCentro;
        try {
            AlquilerDTO[] res = restTemplate.getForObject(url, AlquilerDTO[].class);
            return res != null ? Arrays.asList(res) : Arrays.asList();
        } catch (Exception e) { return Arrays.asList(); }
    }

    public void guardarAlquiler(AlquilerDTO dto) {
        restTemplate.postForObject(getApiUrl() + "/alquileres", dto, AlquilerDTO.class);
    }

    public void eliminarAlquiler(Integer id) {
        restTemplate.delete(getApiUrl() + "/alquileres/" + id);
    }

    // --- PLANTAS ---
    public List<PlantaDTO> listarPlantas(Integer idCentro) {
        String url = getApiUrl() + "/plantas?centroId=" + idCentro;
        try {
            PlantaDTO[] res = restTemplate.getForObject(url, PlantaDTO[].class);
            return res != null ? Arrays.asList(res) : Arrays.asList();
        } catch (Exception e) { return Arrays.asList(); }
    }

    public void guardarPlanta(PlantaDTO dto) {
        restTemplate.postForObject(getApiUrl() + "/plantas", dto, PlantaDTO.class);
    }

    public void eliminarPlanta(Integer id) {
        restTemplate.delete(getApiUrl() + "/plantas/" + id);
    }
}