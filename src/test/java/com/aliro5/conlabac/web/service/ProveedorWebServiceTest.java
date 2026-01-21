package com.aliro5.conlabac.web.service;

// CORRECCIÓN: Importamos el DTO local del proyecto Web
import com.aliro5.conlabac.web.dto.ProveedorDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProveedorWebServiceTest {

    @Autowired
    private ProveedorService service;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    @DisplayName("Debe obtener un proveedor desde el API usando ProveedorDTO")
    void testObtenerProveedorDesdeApi() {
        // CORRECCIÓN: Usar la clase ProveedorDTO definida en tu proyecto
        ProveedorDTO mockP = new ProveedorDTO();
        mockP.setCif("B12345678");
        mockP.setDenominacion("Proveedor Test");

        // Simulamos que el RestTemplate devuelve el DTO al llamar al Backend
        when(restTemplate.getForObject(anyString(), eq(ProveedorDTO.class)))
                .thenReturn(mockP);

        // El servicio web debe devolver un ProveedorDTO
        ProveedorDTO resultado = service.obtener("B12345678", 1);

        // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        //assertEquals("B12345678", resultado.getPrdCif(), "El CIF debe coincidir");
        assertEquals("B12345678", resultado.getCif());
        //assertEquals("Proveedor Test", resultado.getPrdDenominacion());
        assertEquals("Proveedor Test", resultado.getDenominacion());
    }
}