package com.aliro5.conlabac.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class CentroWebService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url.base}")
    private String apiUrlBase;

    public List<Object> listarCentros() {
        // Combinamos la base http://localhost:8080/api con el recurso /centros
        String url = apiUrlBase + "/centros";
        Object[] centros = restTemplate.getForObject(url, Object[].class);
        return Arrays.asList(centros);
    }
}