package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.CentroDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CentroService {

    @Value("${api.url.base:http://localhost:8080/api}")
    private String apiUrlBase;

    private final RestTemplate restTemplate = new RestTemplate();

    // Construimos la URL específica para este servicio
    private String getFullUrl() {
        return apiUrlBase + "/centros";
    }

    public List<CentroDTO> obtenerTodos() {
        // GET http://localhost:8080/api/centros
        CentroDTO[] respuesta = restTemplate.getForObject(getFullUrl(), CentroDTO[].class);
        return (respuesta != null) ? Arrays.asList(respuesta) : Arrays.asList();
    }

    public CentroDTO obtenerPorId(Integer id) {
        // GET http://localhost:8080/api/centros/{id}
        String url = getFullUrl() + "/" + id;
        return restTemplate.getForObject(url, CentroDTO.class);
    }

    public void guardar(CentroDTO centro) {
        // POST http://localhost:8080/api/centros
        restTemplate.postForObject(getFullUrl(), centro, CentroDTO.class);
    }

    public void eliminar(Integer id) {
        // DELETE http://localhost:8080/api/centros/{id}
        restTemplate.delete(getFullUrl() + "/" + id);
    }
}