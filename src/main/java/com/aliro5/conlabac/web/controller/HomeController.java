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

    @Autowired private CentroService centroService;

    @Value("${api.url.base}")
    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        // 1. Obtener Estadísticas
        // URL: http://localhost:10081/api/dashboard/stats?centroId=1
        String statsUrl = baseUrl.replace("/centros", "/dashboard/stats") + "?centroId=" + usuario.getIdCentro();

        DashboardStatsDTO stats = new DashboardStatsDTO();
        try {
            stats = restTemplate.getForObject(statsUrl, DashboardStatsDTO.class);
        } catch (Exception e) {
            e.printStackTrace(); // Si falla la API, mostramos 0
        }

        model.addAttribute("stats", stats);

        // 2. Datos Centro
        CentroDTO centro = centroService.obtenerPorId(usuario.getIdCentro());
        model.addAttribute("nombreCentro", centro.getDenominacion());
        model.addAttribute("usuario", usuario);

        return "home";
    }
}