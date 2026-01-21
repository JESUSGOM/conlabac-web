package com.aliro5.conlabac.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReportService {

    @Value("${api.url.base}")
    private String baseUrl; // http://localhost:8080/api

    private final RestTemplate restTemplate = new RestTemplate();

    private String getApiUrl() {
        // Apuntamos directamente a la ruta definida en el Backend
        return baseUrl + "/reports";
    }

    // Método para Movimientos del día
    public byte[] descargarPdfMovimientos(Integer idCentro) {
        String url = getApiUrl() + "/movimientos/pdf?centroId=" + idCentro;
        try {
            return restTemplate.getForObject(url, byte[].class);
        } catch (Exception e) {
            System.err.println("Error descargando PDF movimientos: " + e.getMessage());
            return null;
        }
    }

    // MÉTODO: INFORME MENSUAL
    public byte[] descargarInformeMensual(int mes, int anio, int idCentro) {
        // Construimos la URL hacia el endpoint /api/reports/mensual
        String url = getApiUrl() + "/mensual?mes=" + mes + "&anio=" + anio + "&centroId=" + idCentro;

        try {
            return restTemplate.getForObject(url, byte[].class);
        } catch (Exception e) {
            System.err.println("Error descargando Informe Mensual: " + e.getMessage());
            return null;
        }
    }
}