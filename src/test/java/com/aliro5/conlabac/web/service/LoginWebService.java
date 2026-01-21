package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginWebService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url.base}")
    private String apiUrlBase;

    public Object validarAcceso(String dni, String password) {
        // La URL final será http://localhost:8080/api/usuarios/login (o el endpoint que definas)
        String url = apiUrlBase + "/usuarios/validar";
        LoginRequest request = new LoginRequest(dni, password);

        try {
            // Enviamos los datos al Backend
            return restTemplate.postForObject(url, request, Object.class);
        } catch (Exception e) {
            return null; // Error de conexión o credenciales inválidas
        }
    }
}