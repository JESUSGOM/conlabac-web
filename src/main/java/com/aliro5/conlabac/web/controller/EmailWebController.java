package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.EmailRequestDTO;
import com.aliro5.conlabac.web.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/email")
public class EmailWebController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url.base:http://localhost:8080/api}")
    private String apiUrl;

    // MODIFICADO: Ahora pasa el centroId a la API para filtrar
    @GetMapping
    public String mostrarPanelEmails(@RequestParam(defaultValue = "0") int page, Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";

        // Incluimos centroId en la llamada REST
        String url = apiUrl + "/envios-email/paginado?centroId=" + usuario.getIdCentro() + "&page=" + page;

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            model.addAttribute("emailsPage", response);
            model.addAttribute("currentPage", page);
        } catch (Exception e) {
            model.addAttribute("mensajeError", "Error al cargar el histórico.");
        }

        model.addAttribute("activeLink", "email");
        return "email-panel";
    }

    @GetMapping("/incidencia")
    public String mostrarPanelIncidencias(@RequestParam(defaultValue = "0") int page, Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";

        String url = apiUrl + "/incidencias/paginado?centroId=" + usuario.getIdCentro() + "&page=" + page;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        model.addAttribute("incidenciasPage", response);
        model.addAttribute("currentPage", page);
        model.addAttribute("nombreCentro", session.getAttribute("nombreCentro"));
        model.addAttribute("activeLink", "incidencias");
        return "incidencias-panel";
    }

    @GetMapping("/incidencia/nueva")
    public String mostrarFormularioIncidencia(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        model.addAttribute("activeLink", "incidencias");
        model.addAttribute("nombreCentro", session.getAttribute("nombreCentro"));
        return "incidencia-form";
    }

    @PostMapping("/incidencia/comunicar")
    public String enviarIncidencia(@RequestParam String mensaje, HttpSession session, RedirectAttributes ra) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        EmailRequestDTO request = new EmailRequestDTO();
        request.setMensaje(mensaje);
        request.setIdCentro(usuario.getIdCentro());
        request.setDniEmisor(usuario.getNombre() + " " + usuario.getApellido1());

        try {
            restTemplate.postForEntity(apiUrl + "/envios-email/incidencia", request, String.class);
            ra.addFlashAttribute("mensajeOk", "Incidencia comunicada correctamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("mensajeError", "Fallo al comunicar incidencia.");
        }
        return "redirect:/email/incidencia";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioEmail(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        model.addAttribute("activeLink", "email");
        model.addAttribute("nombreCentro", session.getAttribute("nombreCentro"));
        return "email-form";
    }
}