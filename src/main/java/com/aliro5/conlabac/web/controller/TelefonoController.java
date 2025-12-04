package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.CentroDTO;
import com.aliro5.conlabac.web.dto.TelefonoDTO;
import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.CentroService;
import com.aliro5.conlabac.web.service.TelefonoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/telefonos")
public class TelefonoController {

    @Autowired private TelefonoService service;
    @Autowired private CentroService centroService;

    @GetMapping
    public String panel(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        model.addAttribute("listaLlamadas", service.listarHistorial(usuario.getIdCentro()));

        CentroDTO centro = centroService.obtenerPorId(usuario.getIdCentro());
        model.addAttribute("nombreCentro", centro.getDenominacion());
        model.addAttribute("usuario", usuario);

        return "telefonos-panel";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        TelefonoDTO dto = new TelefonoDTO();
        dto.setIdCentro(usuario.getIdCentro());

        model.addAttribute("telefono", dto);
        return "telefonos-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute TelefonoDTO telefono, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        telefono.setIdCentro(usuario.getIdCentro());
        service.registrar(telefono);
        return "redirect:/telefonos";
    }

    @GetMapping("/comunicar/{id}")
    public String comunicar(@PathVariable Integer id, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        service.marcarComunicado(id);
        return "redirect:/telefonos";
    }
}