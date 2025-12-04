package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.TelefonoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TelefonoService {

    @Value("${api.url.base}")
    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String getApiUrl() {
        return baseUrl.replace("/centros", "/telefonos");
    }

    public List<TelefonoDTO> listarHistorial(Integer idCentro) {
        // Obtenemos TODO (Pendientes + Histórico) ordenado por fecha
        String url = getApiUrl() + "/historial?centroId=" + idCentro;
        try {
            TelefonoDTO[] res = restTemplate.getForObject(url, TelefonoDTO[].class);
            return res != null ? Arrays.asList(res) : Arrays.asList();
        } catch (Exception e) {
            e.printStackTrace(); return Arrays.asList();
        }
    }

    public void registrar(TelefonoDTO dto) {
        restTemplate.postForObject(getApiUrl(), dto, TelefonoDTO.class);
    }

    public void marcarComunicado(Integer id) {
        String url = getApiUrl() + "/" + id + "/comunicar";
        restTemplate.put(url, null);
    }
}