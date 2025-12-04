package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.EmpleadoProveedorDTO;
import com.aliro5.conlabac.web.dto.ProveedorDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProveedorService {

    @Value("${api.url.base}")
    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String getApiUrl() {
        return baseUrl.replace("/centros", "/proveedores");
    }

    public List<ProveedorDTO> listar(Integer idCentro) {
        String url = getApiUrl() + "?centroId=" + idCentro;
        try {
            ProveedorDTO[] res = restTemplate.getForObject(url, ProveedorDTO[].class);
            return res != null ? Arrays.asList(res) : Arrays.asList();
        } catch (Exception e) {
            e.printStackTrace(); return Arrays.asList();
        }
    }

    public ProveedorDTO obtener(String cif, Integer idCentro) {
        String url = getApiUrl() + "/" + cif + "?centroId=" + idCentro;
        return restTemplate.getForObject(url, ProveedorDTO.class);
    }

    public void guardar(ProveedorDTO dto) {
        restTemplate.postForObject(getApiUrl(), dto, ProveedorDTO.class);
    }

    // Listar empleados de una empresa concreta (por CIF)
    public List<EmpleadoProveedorDTO> listarEmpleados(String cif, Integer idCentro) {
        // GET /api/proveedores/{cif}/empleados?centroId=1
        String url = getApiUrl() + "/" + cif + "/empleados?centroId=" + idCentro;
        try {
            EmpleadoProveedorDTO[] res = restTemplate.getForObject(url, EmpleadoProveedorDTO[].class);
            return res != null ? Arrays.asList(res) : Arrays.asList();
        } catch (Exception e) {
            e.printStackTrace(); return Arrays.asList();
        }
    }

    // Guardar Empleado
    public void guardarEmpleado(EmpleadoProveedorDTO emp) {
        // POST /api/proveedores/empleados
        restTemplate.postForObject(getApiUrl() + "/empleados", emp, EmpleadoProveedorDTO.class);
    }

    // Eliminar Empleado
    public void eliminarEmpleado(Integer id) {
        // DELETE /api/proveedores/empleados/{id}
        restTemplate.delete(getApiUrl() + "/empleados/" + id);
    }

    // --- NUEVO: Listar TODOS los trabajadores del centro ---
    public List<EmpleadoProveedorDTO> listarTodosTrabajadores(Integer idCentro) {
        // GET /api/proveedores/empleados/todos?centroId=1
        String url = getApiUrl() + "/empleados/todos?centroId=" + idCentro;
        try {
            EmpleadoProveedorDTO[] res = restTemplate.getForObject(url, EmpleadoProveedorDTO[].class);
            return res != null ? Arrays.asList(res) : Arrays.asList();
        } catch (Exception e) {
            e.printStackTrace(); return Arrays.asList();
        }
    }
}