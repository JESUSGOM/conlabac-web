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
    private String apiUrlBase; // Debería ser http://localhost:8080/api

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        // Recuperamos el usuario de la sesión
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");

        // Si la sesión ha expirado o no hay usuario, volvemos a la raíz (login)
        if (usuario == null) {
            return "redirect:/";
        }

        // 1. Obtener Estadísticas del Dashboard
        // Construimos la URL: http://localhost:8080/api/dashboard/stats?centroId=X
        String statsUrl = apiUrlBase + "/dashboard/stats?centroId=" + usuario.getIdCentro();

        DashboardStatsDTO stats = new DashboardStatsDTO();
        try {
            // Intentamos obtener datos reales del API
            DashboardStatsDTO respuesta = restTemplate.getForObject(statsUrl, DashboardStatsDTO.class);
            if (respuesta != null) {
                stats = respuesta;
            }
        } catch (Exception e) {
            // Si el endpoint /dashboard/stats no existe o falla, mostramos estadísticas a 0
            System.err.println("Aviso: No se pudieron cargar las estadísticas de la API. Mostrando valores por defecto.");
        }
        model.addAttribute("stats", stats);

        // 2. Datos del Centro (Nombre ITC Tenerife / Las Palmas)
        try {
            CentroDTO centro = centroService.obtenerPorId(usuario.getIdCentro());
            if (centro != null) {
                model.addAttribute("nombreCentro", centro.getDenominacion());
                model.addAttribute("centro", centro);
            } else {
                model.addAttribute("nombreCentro", "Centro " + usuario.getIdCentro());
            }
        } catch (Exception e) {
            // Si falla la carga del centro, ponemos un nombre genérico para no romper la web
            model.addAttribute("nombreCentro", "Centro " + usuario.getIdCentro());
            System.err.println("Error al obtener nombre del centro: " + e.getMessage());
        }

        // 3. Pasamos el usuario a la vista
        model.addAttribute("usuario", usuario);

        // IMPORTANTE: Se devuelve "home" porque tu archivo es home.html
        return "home";
    }
}