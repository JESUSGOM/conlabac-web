package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.MovimientoEmpleadoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContratasService {

    @Value("${api.url.base}")
    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String getApiUrl() {
        return baseUrl.replace("/centros", "/contratas");
    }

    // Listar gente DENTRO
    public List<MovimientoEmpleadoDTO> listarActivos(Integer idCentro) {
        String url = getApiUrl() + "/activos?centroId=" + idCentro;
        try {
            MovimientoEmpleadoDTO[] res = restTemplate.getForObject(url, MovimientoEmpleadoDTO[].class);
            return res != null ? Arrays.asList(res) : Arrays.asList();
        } catch (Exception e) {
            e.printStackTrace(); return Arrays.asList();
        }
    }

    // Registrar Entrada
    public void registrarEntrada(String nif, String cif, Integer centroId) {
        Map<String, Object> body = new HashMap<>();
        body.put("nif", nif);
        body.put("cif", cif);
        body.put("centroId", centroId);

        restTemplate.postForObject(getApiUrl() + "/entrada", body, MovimientoEmpleadoDTO.class);
    }

    // Registrar Salida
    public void registrarSalida(Integer idMovimiento) { // Si usas ID directo es más fácil
        // Como la API espera PUT a /salida con JSON de NIF, podemos adaptar la API
        // O mejor, usamos el endpoint de ID que creamos: /salida/{id}
        String url = getApiUrl() + "/salida/" + idMovimiento;
        restTemplate.put(url, null);
    }
}