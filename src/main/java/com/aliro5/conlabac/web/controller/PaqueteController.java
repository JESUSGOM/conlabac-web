package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.CentroDTO;
import com.aliro5.conlabac.web.dto.PaqueteDTO;
import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.CentroService;
import com.aliro5.conlabac.web.service.PaqueteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/paqueteria")
public class PaqueteController {

    @Autowired private PaqueteService service;
    @Autowired private CentroService centroService;

    @GetMapping
    public String panel(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        // Cargar listas: Pendientes (para alertas) y Todo (para tabla)
        model.addAttribute("listaPendientes", service.listarPendientes(usuario.getIdCentro()));
        model.addAttribute("listaHistorial", service.listarHistorial(usuario.getIdCentro()));

        CentroDTO centro = centroService.obtenerPorId(usuario.getIdCentro());
        model.addAttribute("nombreCentro", centro.getDenominacion());
        model.addAttribute("usuario", usuario);

        return "paquetes-panel";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        PaqueteDTO dto = new PaqueteDTO();
        dto.setIdCentro(usuario.getIdCentro());
        // El operario es el usuario logueado
        dto.setOperario(usuario.getNombre());

        model.addAttribute("paquete", dto);
        return "paquetes-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute PaqueteDTO paquete, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        paquete.setIdCentro(usuario.getIdCentro());
        service.recibir(paquete);
        return "redirect:/paqueteria";
    }

    @GetMapping("/entregar/{id}")
    public String entregar(@PathVariable Integer id, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        service.entregar(id);
        return "redirect:/paqueteria";
    }
}