package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.CentroDTO; // Necesario para el nombre
import com.aliro5.conlabac.web.dto.MovimientoDTO;
import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.CentroService;
import com.aliro5.conlabac.web.service.MovimientoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private CentroService centroService;

    @GetMapping
    public String listar(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        // 1. Obtener la lista de movimientos activos
        model.addAttribute("listaMovimientos", movimientoService.obtenerActivosHoy(usuario.getIdCentro()));

        // 2. CAMBIO: Obtener el nombre real del centro para mostrarlo en el título
        CentroDTO centro = centroService.obtenerPorId(usuario.getIdCentro());
        model.addAttribute("nombreCentro", centro.getDenominacion()); // CenDen

        model.addAttribute("usuario", usuario);

        return "movimientos-list";
    }

    // ... El resto de métodos (nuevo, editar, guardar, salida) se mantienen IGUAL ...
    // Solo cambia el método 'listar' de arriba.

    @GetMapping("/nuevo")
    public String nuevo(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        MovimientoDTO mov = new MovimientoDTO();
        mov.setIdCentro(usuario.getIdCentro());

        model.addAttribute("movimiento", mov);
        model.addAttribute("usuario", usuario);

        return "movimientos-form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        MovimientoDTO mov = movimientoService.obtenerPorId(id);
        if (!mov.getIdCentro().equals(usuario.getIdCentro())) {
            return "redirect:/movimientos";
        }

        model.addAttribute("movimiento", mov);
        model.addAttribute("usuario", usuario);

        return "movimientos-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute MovimientoDTO movimiento, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        movimiento.setIdCentro(usuario.getIdCentro());
        movimientoService.guardar(movimiento);
        return "redirect:/movimientos";
    }

    @GetMapping("/salida/{id}")
    public String registrarSalida(@PathVariable Integer id, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        movimientoService.registrarSalida(id);
        return "redirect:/movimientos";
    }
}