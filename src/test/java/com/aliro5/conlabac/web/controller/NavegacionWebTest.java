package com.aliro5.conlabac.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
public class NavegacionWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Debe cargar login y contener el campo DNI")
    void testPaginaLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(content().string(containsString("dni")));
    }

    @Test
    @DisplayName("Acceso al Dashboard con parámetro de centro")
    void testDashboard() throws Exception {
        mockMvc.perform(get("/dashboard").param("centroId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attributeExists("centroId"));
    }
}