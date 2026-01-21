package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.CentroDTO;
import com.aliro5.conlabac.web.dto.GarajeDTO;
import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.CentroService;
import com.aliro5.conlabac.web.service.GarajeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/garaje")
public class GarajeController {

    @Autowired private GarajeService service;
    @Autowired private CentroService centroService;

    @GetMapping
    public String panel(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        model.addAttribute("listaVehiculos", service.listar());

        CentroDTO centro = centroService.obtenerPorId(usuario.getIdCentro());
        model.addAttribute("nombreCentro", (centro != null) ? centro.getDenominacion() : "Centro Aliros");
        model.addAttribute("usuario", usuario);
        model.addAttribute("activeLink", "garaje");

        return "garaje-panel";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        model.addAttribute("vehiculo", new GarajeDTO());
        model.addAttribute("activeLink", "garaje");
        return "garaje-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute GarajeDTO vehiculo, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        // CORRECCIÓN: Asignamos el ID del centro del usuario logueado
        // Asegúrate de que GarajeDTO.java tenga el método setIdCentro
        vehiculo.setIdCentro(usuario.getIdCentro());

        service.guardar(vehiculo);
        return "redirect:/garaje";
    }
}