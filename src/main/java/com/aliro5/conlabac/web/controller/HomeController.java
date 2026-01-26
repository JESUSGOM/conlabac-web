package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.CentroDTO;
import com.aliro5.conlabac.web.dto.DashboardStatsDTO;
import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.CentroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {

    @Autowired
    private CentroService centroService;

    @Value("${api.url.base}")
    private String apiUrlBase; // http://localhost:8080/api

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        // 1. Verificación de Seguridad: Recuperamos el usuario de la sesión
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/";
        }

        Integer idCentro = usuario.getIdCentro();

        // 2. Obtener Estadísticas del Dashboard (incluyendo el nuevo conteo de llaves)
        DashboardStatsDTO stats = new DashboardStatsDTO();
        try {
            // URL esperada: http://localhost:8080/api/dashboard/stats?centroId=X
            String statsUrl = apiUrlBase + "/dashboard/stats?centroId=" + idCentro;
            DashboardStatsDTO respuesta = restTemplate.getForObject(statsUrl, DashboardStatsDTO.class);
            if (respuesta != null) {
                stats = respuesta;
            }
        } catch (Exception e) {
            System.err.println("Aviso: Error al conectar con el endpoint de estadísticas: " + e.getMessage());
            // El objeto 'stats' ya está inicializado con valores por defecto (0)
        }
        model.addAttribute("stats", stats);

        // 3. Obtener Datos del Centro (Nombre e información general)
        try {
            CentroDTO centro = centroService.obtenerPorId(idCentro);
            if (centro != null) {
                model.addAttribute("nombreCentro", centro.getDenominacion());
                model.addAttribute("centro", centro);
            } else {
                model.addAttribute("nombreCentro", "Centro descononcido (" + idCentro + ")");
            }
        } catch (Exception e) {
            model.addAttribute("nombreCentro", "Error al cargar centro");
            System.err.println("Error al obtener datos del centro: " + e.getMessage());
        }

        // 4. Pasar datos de sesión y usuario a la vista
        model.addAttribute("usuario", usuario);

        return "home";
    }
}