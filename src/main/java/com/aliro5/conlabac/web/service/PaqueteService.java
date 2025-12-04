package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.PaqueteDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PaqueteService {

    @Value("${api.url.base}")
    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String getApiUrl() {
        return baseUrl.replace("/centros", "/paqueteria");
    }

    public List<PaqueteDTO> listarPendientes(Integer idCentro) {
        String url = getApiUrl() + "/pendientes?centroId=" + idCentro;
        try {
            PaqueteDTO[] res = restTemplate.getForObject(url, PaqueteDTO[].class);
            return res != null ? Arrays.asList(res) : Arrays.asList();
        } catch (Exception e) {
            e.printStackTrace(); return Arrays.asList();
        }
    }

    public List<PaqueteDTO> listarHistorial(Integer idCentro) {
        String url = getApiUrl() + "/historial?centroId=" + idCentro;
        try {
            PaqueteDTO[] res = restTemplate.getForObject(url, PaqueteDTO[].class);
            return res != null ? Arrays.asList(res) : Arrays.asList();
        } catch (Exception e) {
            e.printStackTrace(); return Arrays.asList();
        }
    }

    public void recibir(PaqueteDTO dto) {
        restTemplate.postForObject(getApiUrl(), dto, PaqueteDTO.class);
    }

    public void entregar(Integer id) {
        String url = getApiUrl() + "/" + id + "/entregar";
        restTemplate.put(url, null);
    }
}