package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.IncidenciaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class IncidenciaService {

    @Value("${api.url.base}")
    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String getApiUrl() {
        return baseUrl.replace("/centros", "/incidencias");
    }

    public List<IncidenciaDTO> listarPorCentro(Integer idCentro) {
        String url = getApiUrl() + "?centroId=" + idCentro;
        IncidenciaDTO[] res = restTemplate.getForObject(url, IncidenciaDTO[].class);
        return res != null ? Arrays.asList(res) : Arrays.asList();
    }

    public void guardar(IncidenciaDTO dto) {
        restTemplate.postForObject(getApiUrl(), dto, IncidenciaDTO.class);
    }

    public IncidenciaDTO obtenerPorId(Integer id) {
        return restTemplate.getForObject(getApiUrl() + "/" + id, IncidenciaDTO.class);
    }
}