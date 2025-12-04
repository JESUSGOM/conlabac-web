package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.CentroDTO;
import com.aliro5.conlabac.web.dto.EntreTurnoDTO;
import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.CentroService;
import com.aliro5.conlabac.web.service.EntreTurnoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/relevos")
public class EntreTurnoController {

    @Autowired private EntreTurnoService service;
    @Autowired private CentroService centroService;

    @GetMapping
    public String panel(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        // NOTA: Para el grid unificado, solo necesitamos el historial,
        // ya que la API del historial devuelve TODOS (leídos y no leídos).
        model.addAttribute("listaHistorial", service.listarHistorial(usuario.getIdCentro()));

        CentroDTO centro = centroService.obtenerPorId(usuario.getIdCentro());
        if (centro != null) {
            model.addAttribute("nombreCentro", centro.getDenominacion());
        }
        model.addAttribute("usuario", usuario);

        return "relevos-panel";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        EntreTurnoDTO dto = new EntreTurnoDTO();
        dto.setIdCentro(usuario.getIdCentro());

        // --- VISUALIZAR NOMBRE EN EL FORMULARIO ---
        // Aquí seteamos el NOMBRE para que el usuario vea "Juan Pérez" en el input
        dto.setOperarioEscritor(usuario.getNombre() + " " + usuario.getApellido1());

        model.addAttribute("relevo", dto);
        return "relevos-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute EntreTurnoDTO relevo, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        relevo.setIdCentro(usuario.getIdCentro());

        // --- GUARDAR DNI EN LA BASE DE DATOS ---
        // Ignoramos lo que venga en el input (que es el nombre) y forzamos el DNI
        relevo.setOperarioEscritor(usuario.getDni());

        service.guardar(relevo);
        return "redirect:/relevos";
    }

    @GetMapping("/leer/{id}")
    public String marcarLeido(@PathVariable Integer id, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        service.marcarLeido(id, usuario.getDni());

        return "redirect:/relevos";
    }
}