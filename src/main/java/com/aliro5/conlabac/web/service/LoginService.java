package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Value("${api.url.base}") // Esto apunta a http://localhost:10081/api/centros (ojo, hay que ajustar)
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public UsuarioDTO login(String dni, String clave) {
        // Truco: Como la base es ".../api/centros", subimos un nivel para ir a ".../api/auth/login"
        // Lo ideal sería tener una variable solo para el host, pero esto funciona rápido:
        String loginUrl = "http://localhost:8080/api/auth/login";

        // Preparamos el JSON a enviar
        Map<String, String> credenciales = new HashMap<>();
        credenciales.put("dni", dni);
        credenciales.put("clave", clave);

        try {
            // Enviamos POST y esperamos recibir un UsuarioDTO
            return restTemplate.postForObject(loginUrl, credenciales, UsuarioDTO.class);
        } catch (HttpClientErrorException e) {
            // Si devuelve 401 (No autorizado) o 404, retornamos null
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}