package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UsuarioAdminService {

    @Value("${api.url.base}")
    private String apiUrlBase; // http://localhost:8080/api

    private final RestTemplate restTemplate = new RestTemplate();

    private String getFullUrl() {
        return apiUrlBase + "/usuarios";
    }

    // 1. Listar todos los usuarios
    public List<UsuarioDTO> listarTodos() {
        String url = getFullUrl();
        try {
            UsuarioDTO[] res = restTemplate.getForObject(url, UsuarioDTO[].class);
            return (res != null) ? Arrays.asList(res) : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("USUARIOS: Error al listar desde " + url + " -> " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // 2. Obtener un usuario por DNI
    public UsuarioDTO obtenerPorDni(String dni) {
        String url = getFullUrl() + "/" + dni;
        try {
            return restTemplate.getForObject(url, UsuarioDTO.class);
        } catch (Exception e) {
            System.err.println("USUARIOS: Error al obtener DNI " + dni);
            return null;
        }
    }

    // 3. Guardar (Crear o Editar)
    public void guardar(UsuarioDTO usuario) {
        try {
            restTemplate.postForObject(getFullUrl(), usuario, UsuarioDTO.class);
        } catch (Exception e) {
            System.err.println("USUARIOS: Error al guardar usuario -> " + e.getMessage());
        }
    }

    // 4. Eliminar Usuario
    public void eliminar(String dni) {
        String url = getFullUrl() + "/" + dni;
        try {
            restTemplate.delete(url);
        } catch (Exception e) {
            System.err.println("USUARIOS: Error al eliminar DNI " + dni);
        }
    }
}