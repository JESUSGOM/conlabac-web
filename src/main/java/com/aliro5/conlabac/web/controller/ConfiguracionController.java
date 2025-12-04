package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.*;
import com.aliro5.conlabac.web.service.CentroService;
import com.aliro5.conlabac.web.service.ConfiguracionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/configuracion")
public class ConfiguracionController {

    @Autowired private ConfiguracionService configService;
    @Autowired private CentroService centroService;

    // Seguridad: Solo Admin Z
    private boolean esAdmin(HttpSession session) {
        UsuarioDTO u = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        return u != null && "Z".equalsIgnoreCase(u.getTipo());
    }

    // PANEL PRINCIPAL (Dos pestañas)
    @GetMapping
    public String panel(Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");

        model.addAttribute("listaAlquileres", configService.listarAlquileres(usuario.getIdCentro()));
        model.addAttribute("listaPlantas", configService.listarPlantas(usuario.getIdCentro()));

        CentroDTO centro = centroService.obtenerPorId(usuario.getIdCentro());
        model.addAttribute("nombreCentro", centro.getDenominacion());
        model.addAttribute("usuario", usuario);

        return "configuracion-panel";
    }

    // --- GESTIÓN ALQUILERES ---
    @GetMapping("/alquileres/nuevo")
    public String nuevoAlquiler(Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";
        model.addAttribute("alquiler", new AlquilerDTO());
        return "configuracion-alquiler-form";
    }

    @PostMapping("/alquileres/guardar")
    public String guardarAlquiler(@ModelAttribute AlquilerDTO dto, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";
        UsuarioDTO u = (UsuarioDTO) session.getAttribute("usuarioLogueado");

        dto.setIdCentro(u.getIdCentro());
        configService.guardarAlquiler(dto);
        return "redirect:/admin/configuracion";
    }

    @GetMapping("/alquileres/eliminar/{id}")
    public String eliminarAlquiler(@PathVariable Integer id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";
        configService.eliminarAlquiler(id);
        return "redirect:/admin/configuracion";
    }

    // --- GESTIÓN PLANTAS ---
    @GetMapping("/plantas/nueva")
    public String nuevaPlanta(Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";
        model.addAttribute("planta", new PlantaDTO());
        return "configuracion-planta-form";
    }

    @PostMapping("/plantas/guardar")
    public String guardarPlanta(@ModelAttribute PlantaDTO dto, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";
        UsuarioDTO u = (UsuarioDTO) session.getAttribute("usuarioLogueado");

        dto.setIdCentro(u.getIdCentro());
        configService.guardarPlanta(dto);
        return "redirect:/admin/configuracion";
    }

    @GetMapping("/plantas/eliminar/{id}")
    public String eliminarPlanta(@PathVariable Integer id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";
        configService.eliminarPlanta(id);
        return "redirect:/admin/configuracion";
    }
}