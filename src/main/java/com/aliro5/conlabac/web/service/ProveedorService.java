package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.EmpleadoProveedorDTO;
import com.aliro5.conlabac.web.dto.ProveedorDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

@Service
public class ProveedorService {

    @Value("${api.url.base}")
    private String apiUrlBase;

    private final RestTemplate restTemplate = new RestTemplate();

    private String getFullUrl() {
        return apiUrlBase + "/proveedores";
    }

    public List<ProveedorDTO> listar(Integer idCentro) {
        String url = getFullUrl() + "?centroId=" + idCentro;
        try {
            ProveedorDTO[] res = restTemplate.getForObject(url, ProveedorDTO[].class);
            return (res != null) ? Arrays.asList(res) : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("PROVEEDORES: Error cargando lista -> " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public ProveedorDTO obtener(String cif, Integer idCentro) {
        String url = getFullUrl() + "/" + cif + "?centroId=" + idCentro;
        try {
            return restTemplate.getForObject(url, ProveedorDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void guardar(ProveedorDTO dto) {
        try {
            restTemplate.postForObject(getFullUrl(), dto, ProveedorDTO.class);
        } catch (Exception e) {
            System.err.println("PROVEEDORES: Error al guardar");
        }
    }

    public List<EmpleadoProveedorDTO> listarEmpleados(String cif, Integer idCentro) {
        String url = getFullUrl() + "/" + cif + "/empleados?centroId=" + idCentro;
        try {
            EmpleadoProveedorDTO[] res = restTemplate.getForObject(url, EmpleadoProveedorDTO[].class);
            return (res != null) ? Arrays.asList(res) : new ArrayList<>();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public void guardarEmpleado(EmpleadoProveedorDTO emp) {
        restTemplate.postForObject(getFullUrl() + "/empleados", emp, EmpleadoProveedorDTO.class);
    }

    public void eliminarEmpleado(Integer id) {
        restTemplate.delete(getFullUrl() + "/empleados/" + id);
    }

    public List<EmpleadoProveedorDTO> listarTodosTrabajadores(Integer idCentro) {
        String url = getFullUrl() + "/empleados/todos?centroId=" + idCentro;
        try {
            EmpleadoProveedorDTO[] res = restTemplate.getForObject(url, EmpleadoProveedorDTO[].class);
            return (res != null) ? Arrays.asList(res) : new ArrayList<>();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}