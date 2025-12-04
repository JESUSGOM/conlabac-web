package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.KeyMoveDTO;
import com.aliro5.conlabac.web.dto.LlaveDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class KeyMoveService {

    @Value("${api.url.base}") // http://localhost:10081/api/centros
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    private String getApiUrl() {
        if (baseUrl == null) return "http://localhost:10081/api/keymoves";
        return baseUrl.replace("/centros", "/keymoves");
    }

    // 1. Obtener préstamos activos (sin devolver)
    public List<KeyMoveDTO> listarActivos(Integer idCentro) {
        String url = getApiUrl() + "/activos?centroId=" + idCentro;
        try {
            KeyMoveDTO[] res = restTemplate.getForObject(url, KeyMoveDTO[].class);
            return res != null ? Arrays.asList(res) : Arrays.asList();
        } catch (Exception e) {
            e.printStackTrace();
            return Arrays.asList();
        }
    }

    // 2. Obtener llaves DISPONIBLES (para el desplegable)
    public List<LlaveDTO> listarDisponibles(Integer idCentro) {
        String url = getApiUrl() + "/disponibles?centroId=" + idCentro;
        try {
            LlaveDTO[] res = restTemplate.getForObject(url, LlaveDTO[].class);
            return res != null ? Arrays.asList(res) : Arrays.asList();
        } catch (Exception e) {
            e.printStackTrace();
            return Arrays.asList();
        }
    }

    // 3. Registrar Entrega (Salida)
    public void entregar(KeyMoveDTO dto) {
        restTemplate.postForObject(getApiUrl(), dto, KeyMoveDTO.class);
    }

    // 4. Registrar Devolución (Entrada)
    public void devolver(Integer id) {
        String url = getApiUrl() + "/" + id + "/devolver";
        restTemplate.put(url, null);
    }

    public List<KeyMoveDTO> listarActivosHoy(Integer idCentro) {
        // Llamamos a /api/keymoves/activos-hoy
        String url = getApiUrl() + "/activos-hoy?centroId=" + idCentro;
        try {
            KeyMoveDTO[] res = restTemplate.getForObject(url, KeyMoveDTO[].class);
            return res != null ? Arrays.asList(res) : Arrays.asList();
        } catch (Exception e) {
            e.printStackTrace();
            return Arrays.asList();
        }
    }
}