package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.AperturaExtraDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class AperturaExtraService {

    @Value("${api.url.base}")
    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String getApiUrl() { return baseUrl.replace("/centros", "/aperturas"); }

    public List<AperturaExtraDTO> listar(Integer idCentro) {
        String url = getApiUrl() + "?centroId=" + idCentro;
        try {
            AperturaExtraDTO[] res = restTemplate.getForObject(url, AperturaExtraDTO[].class);
            return res != null ? Arrays.asList(res) : Arrays.asList();
        } catch (Exception e) { return Arrays.asList(); }
    }

    public void guardar(AperturaExtraDTO dto) {
        restTemplate.postForObject(getApiUrl(), dto, AperturaExtraDTO.class);
    }

    public void eliminar(Integer id) {
        restTemplate.delete(getApiUrl() + "/" + id);
    }
}