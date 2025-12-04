package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UsuarioAdminService {

    @Value("${api.url.base}") // Recoge la URL base (http://localhost:10081/api/centros)
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // Método auxiliar para apuntar al endpoint correcto (/api/usuarios)
    private String getApiUrl() {
        if (baseUrl == null) return "http://localhost:10081/api/usuarios";
        return baseUrl.replace("/centros", "/usuarios");
    }

    // 1. Listar todos los usuarios
    public List<UsuarioDTO> listarTodos() {
        try {
            UsuarioDTO[] res = restTemplate.getForObject(getApiUrl(), UsuarioDTO[].class);
            return res != null ? Arrays.asList(res) : Arrays.asList();
        } catch (Exception e) {
            e.printStackTrace();
            return Arrays.asList();
        }
    }

    // 2. Obtener un usuario por DNI (Para editar)
    public UsuarioDTO obtenerPorDni(String dni) {
        String url = getApiUrl() + "/" + dni;
        return restTemplate.getForObject(url, UsuarioDTO.class);
    }

    // 3. Guardar (Crear o Editar)
    public void guardar(UsuarioDTO usuario) {
        // Enviamos el objeto a la API.
        // Si lleva el campo 'clave' relleno, la API se encargará de encriptarlo.
        restTemplate.postForObject(getApiUrl(), usuario, UsuarioDTO.class);
    }

    // 4. Eliminar Usuario
    public void eliminar(String dni) {
        String url = getApiUrl() + "/" + dni;
        restTemplate.delete(url);
    }
}