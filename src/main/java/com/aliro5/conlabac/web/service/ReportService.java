package com.aliro5.conlabac.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReportService {

    @Value("${api.url.base}")
    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String getApiUrl() {
        return baseUrl.replace("/centros", "/reports");
    }

    // Método existente (Movimientos del día)
    public byte[] descargarPdfMovimientos(Integer idCentro) {
        String url = getApiUrl() + "/movimientos/pdf?centroId=" + idCentro;
        return restTemplate.getForObject(url, byte[].class);
    }

    // MÉTODO: INFORME MENSUAL  ---
    public byte[] descargarInformeMensual(int mes, int anio, int idCentro) {
        // Construimos la URL manualmente para evitar el error de UriComponentsBuilder
        String url = getApiUrl() + "/mensual?mes=" + mes + "&anio=" + anio + "&centroId=" + idCentro;

        return restTemplate.getForObject(url, byte[].class);
    }
}