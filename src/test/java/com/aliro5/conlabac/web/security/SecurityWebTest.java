package com.aliro5.conlabac.web.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testProteccionDashboard() throws Exception {
        // Al no estar autenticados, debería redirigir al login (302) o dar error (401/403)
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection());
    }
}