package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.EntreTurnoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntreTurnoService {

    @Value("${api.url.base}")
    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String getApiUrl() {
        return baseUrl.replace("/centros", "/entreturnos");
    }

    public List<EntreTurnoDTO> listarHistorial(Integer idCentro) {
        String url = getApiUrl() + "/historial?centroId=" + idCentro;
        EntreTurnoDTO[] res = restTemplate.getForObject(url, EntreTurnoDTO[].class);
        return res != null ? Arrays.asList(res) : Arrays.asList();
    }

    public List<EntreTurnoDTO> listarPendientes(Integer idCentro) {
        String url = getApiUrl() + "/pendientes?centroId=" + idCentro;
        EntreTurnoDTO[] res = restTemplate.getForObject(url, EntreTurnoDTO[].class);
        return res != null ? Arrays.asList(res) : Arrays.asList();
    }

    public void guardar(EntreTurnoDTO dto) {
        restTemplate.postForObject(getApiUrl(), dto, EntreTurnoDTO.class);
    }

    // --- CORRECCIÓN IMPORTANTE ---
    // Hemos cambiado el nombre del parámetro a 'dniUsuario' para recordar que
    // a la API solo se le debe enviar el DNI (max 9 chars), NO el nombre completo.
    public void marcarLeido(Integer id, String dniUsuario) {
        String url = getApiUrl() + "/" + id + "/leer";

        Map<String, String> body = new HashMap<>();
        // La API espera la clave "lector", pero le enviamos el DNI.
        body.put("lector", dniUsuario);

        restTemplate.put(url, body);
    }
}