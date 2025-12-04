package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.AperturaExtraDTO;
import com.aliro5.conlabac.web.dto.CentroDTO;
import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.AperturaExtraService;
import com.aliro5.conlabac.web.service.CentroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/aperturas")
public class AperturaExtraController {

    @Autowired private AperturaExtraService service;
    @Autowired private CentroService centroService;

    // Seguridad: Solo Z o Y
    private boolean esAutorizado(HttpSession session) {
        UsuarioDTO u = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        return u != null && ("Z".equalsIgnoreCase(u.getTipo()) || "Y".equalsIgnoreCase(u.getTipo()));
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {
        if (!esAutorizado(session)) return "redirect:/home";
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");

        model.addAttribute("listaAperturas", service.listar(usuario.getIdCentro()));

        CentroDTO centro = centroService.obtenerPorId(usuario.getIdCentro());
        model.addAttribute("nombreCentro", centro.getDenominacion());
        model.addAttribute("usuario", usuario);

        return "aperturas-panel";
    }

    @GetMapping("/nueva")
    public String nueva(Model model, HttpSession session) {
        if (!esAutorizado(session)) return "redirect:/home";
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");

        AperturaExtraDTO dto = new AperturaExtraDTO();
        dto.setIdCentro(usuario.getIdCentro());

        model.addAttribute("apertura", dto);
        return "aperturas-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute AperturaExtraDTO apertura, HttpSession session) {
        if (!esAutorizado(session)) return "redirect:/home";
        UsuarioDTO u = (UsuarioDTO) session.getAttribute("usuarioLogueado");

        apertura.setIdCentro(u.getIdCentro());
        service.guardar(apertura);
        return "redirect:/aperturas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, HttpSession session) {
        if (!esAutorizado(session)) return "redirect:/home";
        service.eliminar(id);
        return "redirect:/aperturas";
    }
}