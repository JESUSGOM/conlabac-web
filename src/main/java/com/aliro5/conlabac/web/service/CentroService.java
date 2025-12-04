package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.CentroDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CentroService {

    @Value("${api.url.base}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<CentroDTO> obtenerTodos() {
        // Hace una petición GET a http://localhost:10081/api/centros
        CentroDTO[] respuesta = restTemplate.getForObject(apiUrl, CentroDTO[].class);

        if (respuesta != null) {
            return Arrays.asList(respuesta);
        } else {
            return Arrays.asList();
        }
    }

    // --- NUEVO MÉTODO PARA EDITAR ---
    public CentroDTO obtenerPorId(Integer id) {
        // GET http://localhost:10081/api/centros/{id}
        // Pide un único objeto a la API para rellenar el formulario
        return restTemplate.getForObject(apiUrl + "/" + id, CentroDTO.class);
    }

    public void guardar(CentroDTO centro) {
        // Enviamos el objeto 'centro' a la API usando POST.
        // Si el objeto lleva ID, el backend (JPA) lo actualizará.
        // Si no lleva ID, lo creará nuevo.
        restTemplate.postForObject(apiUrl, centro, CentroDTO.class);
    }

    public void eliminar(Integer id) {
        // Llama a DELETE http://localhost:10081/api/centros/{id}
        restTemplate.delete(apiUrl + "/" + id);
    }
}