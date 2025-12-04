package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.CentroDTO;
import com.aliro5.conlabac.web.dto.IncidenciaDTO;
import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.CentroService;
import com.aliro5.conlabac.web.service.IncidenciaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/incidencias")
public class IncidenciaController {

    @Autowired private IncidenciaService incidenciaService;
    @Autowired private CentroService centroService;

    // LISTAR
    @GetMapping
    public String listar(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        model.addAttribute("listaIncidencias", incidenciaService.listarPorCentro(usuario.getIdCentro()));
        CentroDTO centro = centroService.obtenerPorId(usuario.getIdCentro());
        model.addAttribute("nombreCentro", centro.getDenominacion());
        model.addAttribute("usuario", usuario);

        return "incidencias-panel";
    }

    // NUEVA INCIDENCIA
    @GetMapping("/nueva")
    public String nueva(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        IncidenciaDTO dto = new IncidenciaDTO();
        dto.setIdCentro(usuario.getIdCentro());

        // Pre-cargamos el nombre del usuario para enviarlo a la API
        // (Esto es lo que usará el email en el campo "FROM")
        dto.setUsuario(usuario.getNombre() + " " + usuario.getApellido1());

        model.addAttribute("incidencia", dto);
        return "incidencias-form";
    }

    // VER DETALLE
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Integer id, Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        model.addAttribute("incidencia", incidenciaService.obtenerPorId(id));
        return "incidencias-form";
    }

    // GUARDAR (Esto dispara el email en la API)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute IncidenciaDTO incidencia, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        // Aseguramos el centro del usuario
        incidencia.setIdCentro(usuario.getIdCentro());

        // Enviamos a la API.
        // LA API SE ENCARGARÁ DE DETECTAR EL CENTRO, PONER EL EMAIL Y ENVIARLO.
        incidenciaService.guardar(incidencia);

        return "redirect:/incidencias";
    }
}